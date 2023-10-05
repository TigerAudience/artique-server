package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.UserCreateReview;
import com.artique.api.profile.userReview.UserThumbsReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThumbsUpShortReview {
  private Long reviewId;
  private String musicalName;
  private Double starRating;
  private String shortReview;

  public static ThumbsUpShortReview of(UserThumbsReview r){
    return new ThumbsUpShortReview(r.getReviewId(),r.getMusicalName(),r.getStarRating(),r.getShortReview());
  }
}
