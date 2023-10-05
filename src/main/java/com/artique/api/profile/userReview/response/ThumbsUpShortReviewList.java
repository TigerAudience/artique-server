package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.UserThumbsReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThumbsUpShortReviewList {
  List<ThumbsUpShortReview> reviews;

  public static ThumbsUpShortReviewList of(Slice<UserThumbsReview> r){
    List<UserThumbsReview> thumbsReviews = r.stream().toList();
    List<ThumbsUpShortReview> reviews = thumbsReviews.stream().map(ThumbsUpShortReview::of).toList();
    return new ThumbsUpShortReviewList(reviews);
  }
}
