package com.artique.api.profile.userReview;

import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.profile.userReview.dto.*;
import com.artique.api.profile.userReview.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCreateReviewService {
  private final MemberThumbsUpReviewService memberThumbsUpReviewService;
  private final MemberRepository memberRepository;

  private final ReviewRepository reviewRepository;

  public CreateShortReviewList getCreateShortReviews(String memberId){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    UserCreateReviewSlice reviews = findCreateReviews(memberId,0,5);

    return CreateShortReviewList.of(reviews);
  }
  public CreateReviewList getCreateAllReviews(String memberId, int page, int size,UserReviewOrderBy orderBy){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    UserCreateReviewSlice reviews = findCreateReviews(memberId,page,size,orderBy);

    return CreateReviewList.of(reviews);
  }
  public CreateSearchReviewList getCreateSearchReviews(String memberId, int page, int size, String keyword
          ,UserReviewOrderBy orderBy){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, orderBy.getFieldName()));
    Slice<UserCreateReview> reviews = reviewRepository
            .findUserReviewsByMemberIdAndKeyword(pageRequest,memberId,keyword);

    List<UserCreateReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList,memberId);

    return CreateSearchReviewList
            .of(new UserCreateReviewSlice(reviewList,page,reviewList.size(),reviews.hasNext()));
  }
  public UserCreateReviewSlice findCreateReviews(String memberId, int page, int size){
    return findCreateReviews(memberId,page,size,UserReviewOrderBy.THUMBS);
  }

  public UserCreateReviewSlice findCreateReviews(String memberId, int page, int size,UserReviewOrderBy orderBy){
    //get user thumbs up reviews
    PageRequest pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC, orderBy.getFieldName()));
    Slice<UserCreateReview> reviews = reviewRepository.findUserReviewsByMemberId(pageRequest,memberId);

    //adjust current user is thumbs up to review
    List<UserCreateReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList,memberId);
    return new UserCreateReviewSlice(reviewList,page,reviewList.size(),reviews.hasNext());
  }
  public void mapThumbsId(List<UserCreateReview> reviews,String memberId){
    //adjust current user is thumbs up to review
    List<Long> reviewIds = getReviewIds(reviews);

    List<ReviewThumb> reviewThumbs = reviewRepository.findThumbsByReviewIds(reviewIds,memberId);
    HashMap<Long,Long> reviewThumbMap = toMap(reviewThumbs);

    for(UserCreateReview review: reviews){
      review.adjustThumbsId(reviewThumbMap.get(review.getReviewId()));
    }
  }
  private List<Long> getReviewIds(List<UserCreateReview> reviews){
    return reviews.stream().map(UserCreateReview::getReviewId).collect(Collectors.toList());
  }
  private HashMap<Long, Long> toMap(List<ReviewThumb> reviewThumbs){
    HashMap<Long, Long> reviewThumbMap = new HashMap<>();
    for(ReviewThumb rt : reviewThumbs)
      reviewThumbMap.put(rt.getReviewId(),rt.getThumbsId());
    return reviewThumbMap;
  }
}
