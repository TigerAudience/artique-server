package com.artique.api.report;

import com.artique.api.entity.Member;
import com.artique.api.entity.Report;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.report.dto.ReportException;
import com.artique.api.report.dto.ReportType;
import com.artique.api.slack.SlackMessageSender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  private final ReportRepository reportRepository;
  private final SlackMessageSender slackMessageSender;
  @Transactional
  public void reportReview(Long reviewId, ReportType reportType, String reportMemberId){
    Member reportMember = memberRepository.findById(reportMemberId)
            .orElseThrow(()->new ReportException("invalid member id","REPORT-003"));
    Review review = reviewRepository.findById(reviewId)
            .orElseThrow(()->new ReportException("invalid review id","REPORT-004"));
    Report createdReview = new Report(null,reportType,reportMember,review,LocalDateTime.now());
    reportMember.increaseReportCount();
    reportRepository.save(createdReview);
  }
  public void reportMember(String memberId,String reportMemberId,String reportType){
    String today=ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
    slackMessageSender.sendMessage("신고 정보 ["+today+"]",
            createMessageMap(memberId,reportMemberId,reportType));
  }
  private HashMap<String,String> createMessageMap(String memberId,String reportMemberId,String reportType){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new ReportException("invalid member id","REPORT-003"));
    Member reportMember = memberRepository.findById(reportMemberId)
            .orElseThrow(()->new ReportException("invalid report member id","REPORT-004"));
    HashMap<String,String> message = new HashMap<>();
    message.put("신고 일자", ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    message.put("신고 사유", reportType);
    message.put("피신고자", SlackMemberInfo.of(member).toString());
    message.put("신고자",SlackMemberInfo.of(reportMember).toString());
    return message;
  }
  @AllArgsConstructor
  @Getter
  private static class SlackMemberInfo{
    private String memberId;
    private String nickName;
    private String banDate;
    public static SlackMemberInfo of(Member m){
      ZonedDateTime dateTime = m.getBanDate();
      String dateTimeStr="[not banned user]";
      if(m.getBanDate()!=null)
        dateTimeStr=dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      return new SlackMemberInfo(m.getId(),m.getNickname(),dateTimeStr);
    }
    public String toString(){
      return "id : "+this.memberId+", nickname : "+this.nickName+", ban date : "+banDate;
    }
  }
}
