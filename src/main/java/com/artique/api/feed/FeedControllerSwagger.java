package com.artique.api.feed;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.feed.response.FeedSliceDto;
import com.artique.api.resolver.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FeedControllerSwagger {
  @Operation(summary = "메인 피드 API", description = "메인 페이지에서 피드들을 무한 스크롤 형식으로 받는 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = FeedSliceDto.class))),
          @ApiResponse(responseCode = "400", description = "bad request operation (page, size를 정수로 주어야 합니다.)",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/feed/many-thumbs")
  public FeedSliceDto thumbsFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                                  @RequestParam(value = "size")int size);
  @GetMapping("/feed/long")
  public FeedSliceDto longFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                                @RequestParam(value = "size")int size);
  @GetMapping("/feed/five-star-rating")
  public FeedSliceDto fiveStarFeeds(@LoginUser String memberId, @RequestParam(value = "page")int page,
                                    @RequestParam(value = "size")int size);
}
