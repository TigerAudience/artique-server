package com.artique.api.review.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewUpdateRequest {
  private Long reviewId;
  private Double starRating;
  private String shortReview;
  private String longReview;
  private String casting;
  private LocalDate viewDate;
  private String seat;
}
