package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.dto.UserCreateReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateShortReview {
  private Long reviewId;
  private String musicalName;
  private Double starRating;
  private String shortReview;

  public static CreateShortReview of(UserCreateReview r){
    return new CreateShortReview(r.getReviewId(),r.getMusicalName(),r.getStarRating(),r.getShortReview());
  }
}
