package com.artique.api.review.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewUpdateRequest {
  private Long reviewId;
  private Double starRating;
  private String shortReview;
  private String longReview;
  private List<String> casting;
  private LocalDate viewDate;
  private String seat;
  private Boolean shortSpoiler;
  private Boolean longSpoiler;
}
