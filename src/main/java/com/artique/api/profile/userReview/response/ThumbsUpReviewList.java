package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.dto.UserThumbsReview;
import com.artique.api.profile.userReview.dto.UserThumbsReviewSlice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThumbsUpReviewList {
  private List<ThumbsUpReview> reviews;
  private int page;
  private int size;
  private boolean hasNext;

  public static ThumbsUpReviewList of(UserThumbsReviewSlice reviewSlice){
    List<UserThumbsReview> reviewList = reviewSlice.getReviews();
    List<ThumbsUpReview> reviews = reviewList.stream().map(ThumbsUpReview::of).toList();
    return new ThumbsUpReviewList(reviews,reviewSlice.getPage(),
            reviewSlice.getSize(), reviewSlice.hasNext());
  }
}
