package com.artique.api.profile.userReview;

import com.artique.api.feed.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReviewService {

  private final ReviewRepository reviewRepository;

  public void findThumbsReviews(){
    //get user thumbs up reviews
    PageRequest pageRequest = PageRequest.of(0,10);
    Slice<UserThumbsReview> reviews = reviewRepository.findUserReviewsByThumbs(pageRequest,"abcd");

    //adjust current user is thumbs up to review
    List<UserThumbsReview> reviewList = reviews.stream().toList();

    mapThumbsId(reviewList);
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
