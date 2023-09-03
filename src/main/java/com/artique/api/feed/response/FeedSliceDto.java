package com.artique.api.feed.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FeedSliceDto {
  private List<FeedShorts> feeds;
  private boolean hasNext;
  private int page;
  private int size;
}
