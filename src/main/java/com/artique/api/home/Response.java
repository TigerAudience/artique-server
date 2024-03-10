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
  public static class HomeReviewList{
    private String title;
    private List<HomeReview> reviews;
    public static HomeReviewList of(String title, List<Review> reviews){
      List<HomeReview> recentReviews = reviews.stream().map(HomeReview::of).toList();
      return new HomeReviewList(title, recentReviews);
    }
  }
  @AllArgsConstructor
  @Getter
  private static class HomeReview{
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

    public static HomeReview of(Review review){

      Musical musical = review.getMusical();
      Member member = review.getMember();
      return new HomeReview(
              musical.getId(),musical.getPosterUrl(),musical.getName(),
              review.getId(),review.getViewDate(),review.getStarRating(),review.getThumbsUp(),review.getShortReview(),
              member.getId(),member.getNickname()
      );
    }
  }
}
