package com.artique.api.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedDto {
  private String memberNickname;
  private String shortReview;
  private Integer thumbsCount;
  private String musicalName;
}
