package com.artique.api.home;

import com.artique.api.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

  @AllArgsConstructor
  @Getter
  public static class RecommendMusicalList{
    private Integer size;
    private List<RecommendMusical> recommendMusicals;
    public static RecommendMusicalList of(List<ArtiqueRecommendMusical> r){
      List<RecommendMusical> recommendMusicals = r.stream().map(RecommendMusical::of).toList();
      return new RecommendMusicalList(r.size(),recommendMusicals);
    }
  }

  @AllArgsConstructor
  @Getter
  private static class RecommendMusical{
    private String musicalId;
    private String musicalPosterUrl;
    private String musicalName;
    private Double averageRate;
    private Integer totalReviewCount;
    private Integer sequence;
    private static RecommendMusical of(ArtiqueRecommendMusical recommendMusical){
      Musical musical = recommendMusical.getMusical();
      int reviewCount = musical.getReviews().size();
      double averageRate = calculateAverageRate(musical);
      return new RecommendMusical(musical.getId(),musical.getPosterUrl(),musical.getName(),
              averageRate,reviewCount,recommendMusical.getSequence());
    }
    private static double calculateAverageRate(Musical musical){
      List<Review> reviews = musical.getReviews();
      if(reviews.size()==0)
        return 0D;
      return reviews.stream().collect(Collectors.averagingDouble(Review::getStarRating));
    }
  }

  @AllArgsConstructor
  @Getter
  public static class BannerList{
    List<Banner> banners;
    public static BannerList of(List<HomeBanner> banners){
      return new BannerList(banners.stream().map(Banner::of).toList());
    }
  }
  @AllArgsConstructor
  @Getter
  private static class Banner{
    private String imageUrl;
    private String href;
    private Integer sequence;
    public static Banner of(HomeBanner banner){
      return new Banner(banner.getImageUrl(),banner.getHref(),banner.getSequence());
    }
  }
}
