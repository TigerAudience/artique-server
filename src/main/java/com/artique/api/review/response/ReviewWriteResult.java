package com.artique.api.review.response;

import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewWriteResult {
  private Long reviewId;
  private boolean success;
  public static ReviewWriteResult of(Review r){
    return new ReviewWriteResult(r.getId(),true);
  }
}
