package com.artique.api.report;

import com.artique.api.entity.Member;
import com.artique.api.entity.Report;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.report.dto.ReportException;
import com.artique.api.report.dto.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  private final ReportRepository reportRepository;
  @Transactional
  public void report(Long reviewId, ReportType reportType, String reportMemberId){
    Member reportMember = memberRepository.findById(reportMemberId)
            .orElseThrow(()->new ReportException("invalid member id","REPORT-003"));
    Review review = reviewRepository.findById(reviewId)
            .orElseThrow(()->new ReportException("invalid review id","REPORT-004"));
    Report createdReview = new Report(null,reportType,reportMember,review,LocalDateTime.now());
    reportMember.increaseReportCount();
    reportRepository.save(createdReview);
  }
}
