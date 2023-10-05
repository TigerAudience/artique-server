package com.artique.api.profile.userReview;

import com.artique.api.profile.userReview.response.ThumbsUpShortReviewList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberReviewController {
  private final MemberReviewService memberReviewService;
  @GetMapping("/member/review")
  public ThumbsUpShortReviewList getMemberThumbsShortReviews(@RequestParam String memberId){
    return memberReviewService.getThumbsUpShortReviews(memberId);
  }
}
