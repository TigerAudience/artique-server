package com.artique.api.feed.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FeedShortsDao {
  //member info
  private String memberNickname;
  private String memberImageUrl;
  private String memberId;

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

  //thumbsup info
  private Long thumbsId;
}
