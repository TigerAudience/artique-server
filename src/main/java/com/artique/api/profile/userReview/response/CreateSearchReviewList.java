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
public class CreateSearchReviewList {
  private List<CreateSearchReview> reviews;
  private int page;
  private int size;
  private boolean hasNext;

  public static CreateSearchReviewList of(UserCreateReviewSlice reviewSlice){
    List<UserCreateReview> reviewList = reviewSlice.getReviews();
    List<CreateSearchReview> reviews = reviewList.stream().map(CreateSearchReview::of).toList();
    return new CreateSearchReviewList(reviews,reviewSlice.getPage(),reviewSlice.getSize(),reviewSlice.hasNext());
  }
}
