package com.artique.api.feed;

import com.artique.api.feed.response.FeedSliceDto;
import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController implements FeedControllerSwagger{
  private final FeedService feedService;

  @GetMapping("/feed/recent")
  public FeedSliceDto recentFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                            @RequestParam(value = "size")int size){
    return feedService.mainFeedsWithMember(memberId,page,size,"recent");
  }
  @GetMapping("/feed/many-thumbs")
  public FeedSliceDto thumbsFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                            @RequestParam(value = "size")int size){
    return feedService.mainFeedsWithMember(memberId,page,size,"many-thumbs");
  }
  @GetMapping("/feed/long")
  public FeedSliceDto longFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                            @RequestParam(value = "size")int size){
    return feedService.mainFeedsWithMember(memberId,page,size,"long");
  }
  @GetMapping("/feed/five-star-rating")
  public FeedSliceDto fiveStarFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                            @RequestParam(value = "size")int size){
    return feedService.mainFeedsWithMember(memberId,page,size,"five-star-rating");
  }
}
