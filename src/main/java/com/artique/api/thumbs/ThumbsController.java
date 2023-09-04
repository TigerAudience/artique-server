package com.artique.api.thumbs;

import com.artique.api.resolver.LoginUser;
import com.artique.api.thumbs.dto.ThumbsResponse;
import com.artique.api.thumbs.dto.ThumbsReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThumbsController {
  private final ThumbsService thumbsService;

  @PostMapping("thumbs")
  public ThumbsResponse thumbs(@LoginUser String memberId, @Valid @RequestBody ThumbsReq req){
    return thumbsService.process(memberId,req);
  }
}
