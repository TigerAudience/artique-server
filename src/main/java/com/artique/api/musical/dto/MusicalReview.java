package com.artique.api.musical.dto;

import java.time.LocalDate;

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
