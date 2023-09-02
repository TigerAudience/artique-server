package com.artique.api.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FeedListDto {
  List<FeedDto> feeds;
}
