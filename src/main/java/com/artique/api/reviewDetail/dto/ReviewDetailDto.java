package com.artique.api.reviewDetail.dto;

import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ReviewDetailDto {
  private Long id;
  private String memberNickname;
  private String musicalTitle;
  private String casting;
  private String seat;
  private Double rating;
  private String shortReview;
  private String longReview;
  private LocalDate viewDate;

  public static ReviewDetailDto of(Review r){
    return new ReviewDetailDto(r.getId(),r.getMember().getNickname(),r.getMusical().getName(),r.getCasting(),
            r.getSeat(),r.getStarRating(),r.getShortReview(),r.getLongReview(),r.getViewDate());
  }
}