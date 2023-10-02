package com.artique.api.profile.userReview;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserCreateReview {

  //musical info
  private String musicalName;
  private String posterUrl;
  //casting is review info
  private String casting;
  private String musicalId;

  //review info
  private LocalDate viewDate;
  private Double starRating;
  private Long thumbsCount;
  private String shortReview;
  private Long reviewId;
}
