package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.response.MemberDuplicate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  @GetMapping("member/duplicate")
  public MemberDuplicate checkDuplicateMember(@RequestParam("member-id")String memberId){
    return memberService.checkDuplicateMember(memberId);
  }
}
