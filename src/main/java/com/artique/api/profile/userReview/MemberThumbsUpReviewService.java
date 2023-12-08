package com.artique.api.profile.userReview;

import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.profile.userReview.dto.ReviewThumb;
import com.artique.api.profile.userReview.dto.UserThumbsReview;
import com.artique.api.profile.userReview.dto.UserThumbsReviewSlice;
import com.artique.api.profile.userReview.response.ThumbsUpReviewList;
import com.artique.api.profile.userReview.response.ThumbsUpSearchReviewList;
import com.artique.api.profile.userReview.response.ThumbsUpShortReviewList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberThumbsUpReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;

  public ThumbsUpShortReviewList getThumbsUpShortReviews(String memberId,String loginMemberId){
    memberRepository.findById(memberId).orElseThrow(()->
            new ProfileException("invalid member id ["+(memberId==null ? "null value" : memberId)+"]","PROFILE-001"));

    UserThumbsReviewSlice reviews = findThumbsReviews(memberId,loginMemberId,0,5);

    return ThumbsUpShortReviewList.of(reviews);
  }
  public ThumbsUpReviewList getThumbsUpAllReviews(String memberId,String loginMemberId,int page,int size){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    UserThumbsReviewSlice reviews = findThumbsReviews(memberId,loginMemberId,page,size);

    return ThumbsUpReviewList.of(reviews);
  }
  public ThumbsUpSearchReviewList getThumbsUpSearchReviews(String memberId,String loginMemberId,int page,int size
          ,String keyword){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    PageRequest pageRequest = PageRequest.of(page,size);
    Slice<UserThumbsReview> reviews = reviewRepository
            .findUserReviewsByThumbsAndKeyword(pageRequest,memberId,keyword);

    List<UserThumbsReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList,loginMemberId);

    return ThumbsUpSearchReviewList
            .of(new UserThumbsReviewSlice(reviewList,page,reviewList.size(),reviews.hasNext()));
  }

  public UserThumbsReviewSlice findThumbsReviews(String memberId,String loginMemberId, int page, int size){
    //get user thumbs up reviews
    PageRequest pageRequest = PageRequest.of(page,size);
    Slice<UserThumbsReview> reviews = reviewRepository.findUserReviewsByThumbs(pageRequest,memberId);

    //adjust current user is thumbs up to review
    List<UserThumbsReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList,loginMemberId);
    return new UserThumbsReviewSlice(reviewList,page,reviewList.size(),reviews.hasNext());
  }

  public void mapThumbsId(List<UserThumbsReview> reviews,String loginMemberId){
    //adjust current user is thumbs up to review
    List<Long> reviewIds = getReviewIds(reviews);

    List<ReviewThumb> reviewThumbs = reviewRepository.findThumbsByReviewIds(reviewIds,loginMemberId);
    HashMap<Long,Long> reviewThumbMap = toMap(reviewThumbs);

    for(UserThumbsReview review: reviews){
      review.adjustThumbsId(reviewThumbMap.get(review.getReviewId()));
    }
  }
  private List<Long> getReviewIds(List<UserThumbsReview> reviews){
    return reviews.stream().map(UserThumbsReview::getReviewId).collect(Collectors.toList());
  }
  private HashMap<Long, Long> toMap(List<ReviewThumb> reviewThumbs){
    HashMap<Long, Long> reviewThumbMap = new HashMap<>();
    for(ReviewThumb rt : reviewThumbs)
      reviewThumbMap.put(rt.getReviewId(),rt.getThumbsId());
    return reviewThumbMap;
  }
}
