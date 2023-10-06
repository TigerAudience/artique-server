package com.artique.api.profile.userReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateReviewSlice {
  private List<UserCreateReview> reviews;
  private int page;
  private int size;
  private boolean next;

  public boolean hasNext(){
    return next;
  }
}
