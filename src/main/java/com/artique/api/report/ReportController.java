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
  private final ReportService reportService;
  @PostMapping("/report/review")
  public ReportRes reportReview(@RequestParam(value = "review-id")Long reviewId,
                                @RequestParam(value = "type")ReportType reportType,
                                @LoginUser String reportMemberId){
    reportService.reportReview(reviewId,reportType,reportMemberId);
    return new ReportRes(true);
  }
  @PostMapping("/report/member")
  public ReportRes reportMember(@RequestParam(value = "member-id")String memberId,
                                @RequestParam(value = "type")String reportType,
                                @LoginUser String reportMemberId){
    reportService.reportMember(memberId,reportMemberId,reportType);
    return new ReportRes(true);
  }
}
