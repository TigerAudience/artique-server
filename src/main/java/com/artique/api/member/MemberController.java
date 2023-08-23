package com.artique.api.member;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.MemberDuplicate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger{

  private final MemberService memberService;
  @GetMapping("/member/duplicate")
  public MemberDuplicate checkDuplicateMember(@RequestParam("member-id")
                                                @NotNull(message = "member-id는 필수 입력값입니다.") String memberId){
    return memberService.checkDuplicateMember(memberId);
  }
  @PostMapping("/member/join")
  public JoinMember join(@RequestBody JoinMemberReq memberReq){
    memberService.checkDuplicateMember(memberReq.getMemberId());
    return memberService.join(memberReq);
  }

  @PostMapping("/member/login")
  public LoginMember login(@RequestBody LoginMemberReq memberReq, HttpServletResponse response){
    return memberService.login(memberReq,response);
  }

  @PostMapping("/member/oauth")
  public LoginMember oauth(@RequestBody OauthMemberReq memberReq, HttpServletResponse response){

    return null;
  }
}
