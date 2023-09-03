package com.artique.api.musical;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalRateStatistics;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import com.artique.api.resolver.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface MusicalControllerSwagger {


  @Operation(summary = "작품 상세 정보 API", description = "작품 관련 정보를 받는 API입니다. (별점 통게 & 리뷰 정보 제외)")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = MusicalInfo.class))),
          @ApiResponse(responseCode = "400", description = "올바른 musical id를 주어야 합니다.",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/musical/detail")
  MusicalInfo detail(@RequestParam(value = "musical-id")String musicalId);

  @Operation(summary = "작품 상세 정보 API", description = "하나의 작품에 대한 리뷰들을 조회할 수 있는 API입니다." +
          "좋아요 가장 많이 받은 2개의 리뷰만 보여줍니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = MusicalReviewSmallList.class)))
  })
  @GetMapping("/musical/reviews")
  MusicalReviewSmallList reviews(@LoginUser String memberId, @RequestParam(value = "musical-id")String musicalId);

  @Operation(summary = "작품 별점 통계 정보 API", description = "하나의 작품에 대한 리뷰들의 통계를 확인할 수 있는 API입니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(schema = @Schema(implementation = MusicalRateStatistics.class)))
  })
  @GetMapping("/musical/rate/statistics")
  MusicalRateStatistics rateStatistics(@RequestParam(value = "musical-id")String musicalId);
}
