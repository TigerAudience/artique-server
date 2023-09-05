package com.artique.api.member;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.MemberDuplicate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger{

  private final MemberService memberService;
  @GetMapping("/member/duplicate")
  public MemberDuplicate checkDuplicateMember(@RequestParam(value = "member-id") String memberId){
    if(!memberService.checkDuplicateMember(memberId))
      throw new LoginException("member id duplicate exception", LoginExceptionCode.DUPLICATE_LOGIN_ID.toString());
    return new MemberDuplicate(memberId);
  }
  @PostMapping("/member/join")
  public JoinMember join(@Valid @RequestBody JoinMemberReq memberReq){
    if(!memberService.checkDuplicateMember(memberReq.getMemberId()))
      throw LoginException.builder()
              .errorCode(LoginExceptionCode.DUPLICATE_LOGIN_ID.toString()).message("duplicated member id").build();
    return memberService.join(memberReq);
  }

  @PostMapping("/member/login")
  public LoginMember login(@RequestBody LoginMemberReq memberReq, HttpServletResponse response){
    return memberService.login(memberReq,response);
  }

  @PostMapping("/member/oauth")
  public LoginMember oauth(@RequestBody OauthMemberReq memberReq, HttpServletResponse response){
    return memberService.oauthLogin(memberReq,response);
  }
}
