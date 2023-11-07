package com.artique.api.report;

import com.artique.api.report.dto.ReportResponse;
import com.artique.api.report.dto.ReportResponse.ReportRes;
import com.artique.api.report.dto.ReportType;
import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

  @PostMapping("/report")
  public ReportRes reportReview(@RequestParam(value = "review-id")Long reviewId,
                                @RequestParam(value = "type")ReportType reportType,
                                @LoginUser String reportMemberId){
    return new ReportRes(true);
  }
}
