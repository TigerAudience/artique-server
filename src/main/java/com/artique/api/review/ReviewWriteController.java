package com.artique.api.review;

import com.artique.api.resolver.LoginUser;
import com.artique.api.review.request.ReviewUpdateRequest;
import com.artique.api.review.request.ReviewWriteRequest;
import com.artique.api.review.response.ReviewDeleteResult;
import com.artique.api.review.response.ReviewUpdateResult;
import com.artique.api.review.response.ReviewWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewWriteController {
  private final ReviewWriteService reviewService;
  @PostMapping("write/review")
  public ReviewWriteResult write(@RequestBody ReviewWriteRequest reviewForm, @LoginUser String memberId){
    return reviewService.createReview(reviewForm,memberId);
  }
  @PostMapping("update/review")
  public ReviewUpdateResult update(@RequestBody ReviewUpdateRequest reviewForm, @LoginUser String memberId){
    return reviewService.updateReview(reviewForm,memberId);
  }
  @DeleteMapping("delete/review")
  public ReviewDeleteResult delete(@RequestParam(value = "review-id") Long reviewId, @LoginUser String memberId){
    return new ReviewDeleteResult(reviewService.deleteReview(reviewId,memberId));
  }
}
