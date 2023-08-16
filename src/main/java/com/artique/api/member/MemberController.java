package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.MemberDuplicate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  @GetMapping("member/duplicate")
  public MemberDuplicate checkDuplicateMember(@RequestParam("member-id")String memberId){
    return memberService.checkDuplicateMember(memberId);
  }
  @PostMapping("member/join")
  public JoinMember join(@RequestBody JoinMemberReq memberReq){
    memberService.checkDuplicateMember(memberReq.getMemberId());
    return memberService.join(memberReq);
  }
}
