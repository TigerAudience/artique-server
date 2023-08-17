package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginErrorCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.MemberDuplicate;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final CustomSession session;

  public MemberDuplicate checkDuplicateMember(String memberId){
    if(memberRepository.findById(memberId).isEmpty())
      return new MemberDuplicate(memberId);
    else
      throw new LoginException("member id duplicate exception",LoginErrorCode.DUPLICATE_LOGIN_ID.toString());
  }
  public LoginMember login(LoginMemberReq memberReq, HttpServletResponse response){
    Optional<Member> member = memberRepository.findById(memberReq.getMemberId());
    if (member.isEmpty())
      throw new LoginException("invalid member id",LoginErrorCode.INVALID_MEMBER_ID.toString());
    if (!checkPassword(member.get(),memberReq))
      throw new LoginException("wrong password",LoginErrorCode.INVALID_PASSWORD.toString());

    String sessionId = createSession(member.get());

    adjustCookie(sessionId, response);

    return LoginMember.of(member.get());
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

  private String createSession(Member member){
    return session.createSession(member.getId());
  }
}
