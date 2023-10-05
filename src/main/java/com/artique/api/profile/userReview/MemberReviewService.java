package com.artique.api.profile.userReview;

import com.artique.api.entity.Member;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.profile.userReview.response.ThumbsUpShortReviewList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;

  public ThumbsUpShortReviewList getThumbsUpShortReviews(String memberId){
    memberRepository.findById(memberId).orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    Slice<UserThumbsReview> reviews = findThumbsReviews(0,5);

    return ThumbsUpShortReviewList.of(reviews);
  }

  public Slice<UserThumbsReview> findThumbsReviews(int page,int size){
    //get user thumbs up reviews
    PageRequest pageRequest = PageRequest.of(page,size);
    Slice<UserThumbsReview> reviews = reviewRepository.findUserReviewsByThumbs(pageRequest,"abcd");

    //adjust current user is thumbs up to review
    List<UserThumbsReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList);
    return new SliceImpl<>(reviewList,PageRequest.of(page,size),reviews.hasNext());
  }
  public void searchThumbsReviews(){
    //get user thumbs up reviews
    List<UserThumbsReview> reviews = reviewRepository.searchUserReviewsByThumbs("abcd","keyword");

    mapThumbsId(reviews);
  }

  private void mapThumbsId(List<UserThumbsReview> reviews){
    //adjust current user is thumbs up to review
    List<Long> reviewIds = getReviewIds(reviews);

    List<ReviewThumb> reviewThumbs = reviewRepository.findThumbsByReviewIds(reviewIds);
    HashMap<Long,Long> reviewThumbMap = toMap(reviewThumbs);

    for(UserThumbsReview review: reviews){
      review.adjustThumbsId(reviewThumbMap.get(review.getReviewId()));
    }
  }

  private List<Long> getReviewIds(Slice<UserThumbsReview> reviews){
    return reviews.stream().map(UserThumbsReview::getReviewId).collect(Collectors.toList());
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
