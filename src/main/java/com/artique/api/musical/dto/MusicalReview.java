package com.artique.api.musical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MusicalReview {
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

  //thumbsup info
  private Long thumbsId;
}
