package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginErrorCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.MemberDuplicate;
import com.artique.api.session.CustomSession;
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
    Optional<Member> member = memberRepository.findById(memberId);
    if (member.isEmpty()){
      return new MemberDuplicate(memberId);
    }else{
      throw new LoginException("member id duplicate exception",LoginErrorCode.DUPLICATE_LOGIN_ID.toString());
    }
  }

  // duplicated는 확인 완료 된 상태
  public JoinMember join(JoinMemberReq memberReq){
    JoinMember joinMember = joinMember(memberReq);

    createSession(joinMember);

    return joinMember;
  }

  private JoinMember joinMember(JoinMemberReq memberReq){
    Member member = memberRepository.save(JoinMemberReq.toMember(memberReq));
    return JoinMember.of(member);
  }

  private JoinMember createSession(JoinMember joinMember){
    String sessionId = session.createSession(joinMember.getId());
    joinMember.adjustCookie(sessionId);
    return joinMember;
  }
}
