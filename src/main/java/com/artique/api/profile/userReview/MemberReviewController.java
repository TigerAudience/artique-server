package com.artique.api.profile.userReview;

import com.artique.api.profile.userReview.response.*;
import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberReviewController {
  private final MemberThumbsUpReviewService memberThumbsUpReviewService;
  private final MemberCreateReviewService memberCreateReviewService;
  @GetMapping("/member/review/thumbs/short")
  public ThumbsUpShortReviewList getMemberThumbsShortReviews(@RequestParam("member-id") String memberId
          ,@LoginUser String loginMemberId){
    return memberThumbsUpReviewService.getThumbsUpShortReviews(memberId,loginMemberId);
  }
  @GetMapping("/member/review/thumbs/all")
  public ThumbsUpReviewList getMemberThumbsAllReviews(@RequestParam("member-id") String memberId
          ,@RequestParam int page,@RequestParam int size, @LoginUser String loginMemberId){
    return memberThumbsUpReviewService.getThumbsUpAllReviews(memberId,loginMemberId,page,size);
  }
  @GetMapping("/member/review/thumbs/search")
  public ThumbsUpSearchReviewList getMemberThumbsSearchReviews(@RequestParam("member-id") String memberId
          , @RequestParam int page, @RequestParam int size, @RequestParam("keyword") String keyword
          , @LoginUser String loginMemberId){
    return memberThumbsUpReviewService.getThumbsUpSearchReviews(memberId,loginMemberId,page,size,keyword);
  }

  @GetMapping("/member/review/create/short")
  public CreateShortReviewList getMemberCreateShortReviews(@RequestParam("member-id") String memberId){
    return memberCreateReviewService.getCreateShortReviews(memberId);
  }
  @GetMapping("/member/review/create/all")
  public CreateReviewList getMemberCreateAllReviews(@RequestParam("member-id") String memberId
          , @RequestParam int page, @RequestParam int size
          ,@RequestParam(value = "order-by") UserReviewOrderBy reviewOrderBy){
    return memberCreateReviewService.getCreateAllReviews(memberId,page,size,reviewOrderBy);
  }
  @GetMapping("/member/review/create/search")
  public CreateSearchReviewList getMemberCreateAllReviews(@RequestParam("member-id") String memberId
          , @RequestParam int page, @RequestParam int size, @RequestParam("keyword") String keyword){
    return memberCreateReviewService.getCreateSearchReviews(memberId,page,size,keyword,UserReviewOrderBy.THUMBS);
  }

}
