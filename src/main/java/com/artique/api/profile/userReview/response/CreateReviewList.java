package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.dto.UserCreateReview;
import com.artique.api.profile.userReview.dto.UserCreateReviewSlice;
import com.artique.api.profile.userReview.dto.UserThumbsReview;
import com.artique.api.profile.userReview.dto.UserThumbsReviewSlice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateReviewList {
  private List<CreateReview> reviews;
  private int page;
  private int size;
  private boolean hasNext;

  public static CreateReviewList of(UserCreateReviewSlice reviewSlice){
    List<UserCreateReview> reviewList = reviewSlice.getReviews();
    List<CreateReview> reviews = reviewList.stream().map(CreateReview::of).toList();
    return new CreateReviewList(reviews,reviewSlice.getPage(),reviewSlice.getSize(), reviewSlice.hasNext());
  }
}
