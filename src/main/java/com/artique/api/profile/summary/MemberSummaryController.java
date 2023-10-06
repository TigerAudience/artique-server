package com.artique.api.profile.summary;

import com.artique.api.profile.summary.response.MemberSummary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberSummaryController {

  private final MemberInfoService memberInfoService;

  @GetMapping("/member/summary")
  public MemberSummary getMemberSummary(@RequestParam("member-id")String memberId){
    return memberInfoService.getSummary(memberId);
  }

  @GetMapping("/member/summary/statistics")
  public void getMemberReviewSummary(){
    return;
  }
}
