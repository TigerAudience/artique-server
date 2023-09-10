package com.artique.api.review;

import com.artique.api.entity.Review;
import com.artique.api.review.dto.ReviewDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping("/review")
  public ReviewDetailDto detail(@RequestParam(value = "review-id")Long reviewId){
    return reviewService.getDetail(reviewId);
  }
}
