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
public class CreateShortReviewList {
  List<CreateShortReview> reviews;

  public static CreateShortReviewList of(UserCreateReviewSlice r){
    List<UserCreateReview> createReviews = r.getReviews();
    List<CreateShortReview> reviews = createReviews.stream().map(CreateShortReview::of).toList();
    return new CreateShortReviewList(reviews);
  }
}
