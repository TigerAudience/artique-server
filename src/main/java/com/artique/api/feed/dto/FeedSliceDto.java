package com.artique.api.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FeedSliceDto {
  private List<FeedDto> feeds;
  private boolean hasNext;
  private int page;
  private int size;
}
