package com.artique.api.home;

import com.artique.api.entity.Member;
import com.artique.api.entity.Musical;
import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class Response {
  @AllArgsConstructor
  @Getter
  public static class RecentReviewList{
    private final String title = "최신 리뷰";
    private List<RecentReview> reviews;
    public static RecentReviewList of(List<Review> reviews){
      List<RecentReview> recentReviews = reviews.stream().map(RecentReview::of).toList();
      return new RecentReviewList(recentReviews);
    }
  }
  @AllArgsConstructor
  @Getter
  private static class RecentReview{
    private String musicalId;
    private String musicalImageUrl;
    private String musicalTitle;

    private Long reviewId;
    private LocalDate reviewDate; //String으로 받을 지 LocalDate로 받을 지 android와 의논 필요
    private Double reviewRate;
    private Long thumbsCount;
    private String shortReview;

    private String memberId;
    private String memberNickname;

    public static RecentReview of(Review review){

      Musical musical = review.getMusical();
      Member member = review.getMember();
      return new RecentReview(
              musical.getId(),musical.getPosterUrl(),musical.getName(),
              review.getId(),review.getViewDate(),review.getStarRating(),review.getThumbsUp(),review.getShortReview(),
              member.getId(),member.getNickname()
      );
    }
  }
}
