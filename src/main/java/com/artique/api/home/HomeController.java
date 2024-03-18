package com.artique.api.home;

import com.artique.api.home.Response.HomeReviewList;
import com.artique.api.home.Response.RecommendMusicalList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
  private final HomeFeedService homeFeedService;
  @GetMapping("/home/review/recent")
  public HomeReviewList recentReview(){
    return homeFeedService.findRecentReviews();
  }
  @GetMapping("/home/review/many-thumbs")
  public HomeReviewList manyThumbsReview(){
    return homeFeedService.findManyThumbsUpReviews();
  }
  @GetMapping("/home/review/long")
  public HomeReviewList longReview(){
    return homeFeedService.findIncludingLongReviews();
  }
  @GetMapping("/home/review/five-star-rating")
  public HomeReviewList fiveStarRatingReview(){
    return homeFeedService.findFiveStarRatingReviews();
  }
  @GetMapping("/home/recommend")
  public RecommendMusicalList artiqueRecommend(){
    return homeFeedService.getRecommendMusicalList();
  }
}
