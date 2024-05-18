package com.artique.api.reviewDetail.dto;

import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class ReviewDetailDto {
  private Long id;
  private String memberId;
  private String memberNickname;
  private String musicalPosterUrl;
  private String musicalTitle;
  private String casting;
  private String seat;
  private Double rating;
  private String shortReview;
  private String longReview;
  private LocalDate viewDate;
  private Boolean shortSpoiler;
  private Boolean longSpoiler;
  private Long thumbsCount;

  private Boolean isThumbsUp;
  public static ReviewDetailDto of(Review r, Optional<ReviewDetailThumbsInfo> t){
    return new ReviewDetailDto(r.getId(),
            r.getMember().getId(),r.getMember().getNickname(),
            r.getMusical().getPosterUrl(),r.getMusical().getName(),
            r.getCasting(),r.getSeat(),r.getStarRating(),r.getShortReview(),r.getLongReview(),r.getViewDate(),
            r.isShortSpoiler(), r.isLongSpoiler(),r.getThumbsUp(), t.isPresent());
  }
}
