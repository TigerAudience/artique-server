package com.artique.api.review.response;

import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewUpdateResult {
  private Long reviewId;
  private boolean success;
  public static ReviewUpdateResult of(Review r){
    return new ReviewUpdateResult(r.getId(),true);
  }
}
