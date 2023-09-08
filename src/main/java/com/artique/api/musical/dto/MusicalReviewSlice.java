package com.artique.api.musical.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MusicalReviewSlice {
  private List<MusicalReview> reviews;
  private boolean hasNext;
  private int page;
  private int size;
}
