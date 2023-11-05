package com.artique.api.musical.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class MusicalReviewDao {
  //member info
  private String memberNickname;
  private String memberImageUrl;
  private String memberId;

  //review info
  private LocalDate viewDate;
  private Double starRating;
  private Long thumbsCount;
  private String shortReview;
  private Long reviewId;
  private ZonedDateTime createdAt;
  private Boolean reviewSpoiler;

  //thumbsup info
  private Long thumbsId;
}
