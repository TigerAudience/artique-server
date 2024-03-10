package com.artique.api.home;

import com.artique.api.home.Response.RecentReviewList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
  private final HomeFeedService homeFeedService;
  @GetMapping("/home/review/recent")
  public RecentReviewList recentReview(){
    return homeFeedService.findRecentReviews();
  }
}
