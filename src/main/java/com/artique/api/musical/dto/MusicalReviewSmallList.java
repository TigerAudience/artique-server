package com.artique.api.musical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
public class MusicalReviewSmallList {
  private Long totalReviewCount;
  private List<MusicalReview> reviews;

  public static MusicalReviewSmallList of(Page<MusicalReview> musicalReviews){
    return new MusicalReviewSmallList(musicalReviews.getTotalElements(),musicalReviews.stream().toList());
  }
}
