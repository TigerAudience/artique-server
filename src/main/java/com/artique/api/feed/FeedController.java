package com.artique.api.feed;

import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController implements FeedControllerSwagger{
  private final FeedService feedService;

  @GetMapping("/feed")
  public FeedSliceDto feeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                            @RequestParam(value = "size")int size){
    return feedService.mainFeedsWithMember(memberId,page,size);
  }
}
