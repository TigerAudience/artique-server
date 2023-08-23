package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.oauth.OauthService;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final CustomSession session;
  private final OauthService oauthService;

  public boolean checkDuplicateMember(String memberId){
    return memberRepository.findById(memberId).isEmpty();
  }
  public LoginMember login(LoginMemberReq memberReq, HttpServletResponse response){
    Optional<Member> member = memberRepository.findById(memberReq.getMemberId());
    if (member.isEmpty())
      throw new LoginException("invalid member id", LoginExceptionCode.INVALID_MEMBER_ID.toString());
    if (!checkPassword(member.get(),memberReq))
      throw new LoginException("wrong password", LoginExceptionCode.INVALID_PASSWORD.toString());

    afterLogin(member.get(),response);

    return LoginMember.of(member.get());
  }
  @Transactional
  public LoginMember oauthLogin(OauthMemberReq memberReq, HttpServletResponse httpResponse){

    OauthMember oauthMember = oauthService.getOauthMember(memberReq.getThirdPartyName(),memberReq.getAccessToken());

    JoinMemberReq joinMemberReq = OauthMember.toJoinMemberReq(oauthMember);

    if(checkDuplicateMember(joinMemberReq.getMemberId()))
      join(joinMemberReq);

    Member findMember = memberRepository.findById(joinMemberReq.getMemberId())
            .orElseThrow(() ->
                    LoginException.builder()
                            .message("oauth login failed, cant find member in db")
                            .errorCode(LoginExceptionCode.CANT_FIND_OAUTH_MEMBER.toString())
                            .build()
            );

    afterLogin(findMember,httpResponse);

    return LoginMember.of(findMember);
  }


  public void afterLogin(Member member, HttpServletResponse response){
    String sessionId = session.createSession(member);
    adjustCookie(sessionId, response);
  }

  private boolean checkPassword(Member member,LoginMemberReq memberReq){
    return member.getPassword().equals(memberReq.getMemberPW());
  }
  private void adjustCookie(String sessionId,HttpServletResponse httpServletResponse){
    httpServletResponse.addCookie(new Cookie("session-id",sessionId));
  }

  // duplicated는 확인 완료 된 상태
  public JoinMember join(JoinMemberReq memberReq){
    Member member = memberRepository.save(JoinMemberReq.toMember(memberReq));
    return JoinMember.of(member);
  }
}
