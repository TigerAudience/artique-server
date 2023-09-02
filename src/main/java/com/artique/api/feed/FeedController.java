package com.artique.api.feed;

import com.artique.api.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {
  private final FeedService feedService;

  @GetMapping("/feed")
  public FeedListDto feeds(){
    return feedService.mainFeedsWithMember();
  }
}
