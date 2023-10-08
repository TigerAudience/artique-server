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
public class ThumbsUpSearchReviewList {
  private List<ThumbsUpSearchReview> reviews;
  private int page;
  private int size;
  private boolean hasNext;
  public static ThumbsUpSearchReviewList of(UserThumbsReviewSlice reviewSlice){
    List<UserThumbsReview> reviewList = reviewSlice.getReviews();
    List<ThumbsUpSearchReview> reviews = reviewList.stream().map(ThumbsUpSearchReview::of).toList();
    return new ThumbsUpSearchReviewList(reviews,reviewSlice.getPage(),
            reviewSlice.getSize(), reviewSlice.hasNext());
  }
}
