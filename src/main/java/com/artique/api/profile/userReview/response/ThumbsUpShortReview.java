package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.dto.UserThumbsReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThumbsUpShortReview {
  private Long reviewId;
  private String reviewMemberId;
  private String musicalName;
  private Double starRating;
  private String shortReview;
  private Boolean reviewSpoiler;

  public static ThumbsUpShortReview of(UserThumbsReview r){
    return new ThumbsUpShortReview(r.getReviewId(),r.getMemberId(),r.getMusicalName(),r.getStarRating(),
            r.getShortReview(),r.getReviewSpoiler());
  }
}
