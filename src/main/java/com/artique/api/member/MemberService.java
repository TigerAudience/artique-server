package com.artique.api.member;

import com.artique.api.entity.EmailLog;
import com.artique.api.entity.Member;
import com.artique.api.exception.global.RepositoryException;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.feed.ThumbsRepository;
import com.artique.api.mail.EmailLogRepository;
import com.artique.api.mail.dto.EmailRequest;
import com.artique.api.mail.dto.EmailRequest.VerificationRequest;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.dto.ValidatePasswordRequest;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.UpdateMemberException;
import com.artique.api.member.oauth.OauthService;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.request.UpdateMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.NicknameDuplicate;
import com.artique.api.member.response.UpdateMemberResult;
import com.artique.api.report.ReportRepository;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final CustomSession session;
  private final OauthService oauthService;
  private final MemberGeneratorService memberGeneratorService;
  private final EmailLogRepository emailLogRepository;
  private final ThumbsRepository thumbsRepository;
  private final ReviewRepository reviewRepository;
  private final ReportRepository reportRepository;

  public boolean checkDuplicateMember(String memberId){
    return memberRepository.findById(memberId).isEmpty();
  }
  public boolean isCorrectVerificationNumber(VerificationRequest request){
    EmailLog emailLog = emailLogRepository.findById(request.getEmail())
            .orElseThrow(()->new RuntimeException("fail"));
    if(!compareVerificationNumber(request,emailLog))
      throw new RuntimeException("fail");
    return true;
  }
  public boolean compareVerificationNumber(VerificationRequest request,EmailLog emailLog){
    return request.getCode().equals(emailLog.getCode());
  }
  public LoginMember login(LoginMemberReq memberReq, HttpServletResponse response){
    Member member = memberRepository.findById(memberReq.getMemberId())
            .orElseThrow(() -> LoginException.builder().message("invalid member id")
                    .errorCode(LoginExceptionCode.INVALID_MEMBER_ID.toString()).build());
    if (!checkPassword(member,memberReq))
      throw new LoginException("wrong password", LoginExceptionCode.INVALID_PASSWORD.toString());

    afterLogin(member,response);

    return LoginMember.of(member);
  }
  @Transactional
  public LoginMember oauthLogin(OauthMemberReq memberReq, HttpServletResponse httpResponse){

    OauthMember oauthMember = oauthService.getOauthMember(memberReq.getThirdPartyName(),memberReq.getToken());

    JoinMemberReq joinMemberReq = OauthMember.toJoinMemberReq(oauthMember);

    Member findMember;
    if(checkDuplicateMember(joinMemberReq.getMemberId()))
       findMember = saveMember(joinMemberReq);
    else{
      findMember = memberRepository.findById(joinMemberReq.getMemberId()).get();
    }

    afterLogin(findMember,httpResponse);

    return LoginMember.of(findMember);
  }


  public void afterLogin(Member member, HttpServletResponse response){
    String sessionId = session.createSession(member);
    //adjustCookie(sessionId, response);
    adjustAuthorizationHeader(sessionId,response);
  }

  private boolean checkPassword(Member member,LoginMemberReq memberReq){
    return member.getPassword().equals(memberReq.getMemberPW());
  }
  private void adjustAuthorizationHeader(String sessionId,HttpServletResponse httpServletResponse){
    httpServletResponse.addHeader("Authorization",sessionId);
  }
  private void adjustCookie(String sessionId,HttpServletResponse httpServletResponse){
    httpServletResponse.addCookie(new Cookie("session-id",sessionId));
  }
  private Member saveMember(JoinMemberReq joinMemberReq){
    return memberRepository.save(memberGeneratorService.generateInitialMember(joinMemberReq));
  }

  // duplicated는 확인 완료 된 상태
  public JoinMember join(JoinMemberReq memberReq){
    Member member = saveMember(memberReq);
    return JoinMember.of(member);
  }

  @Transactional
  public UpdateMemberResult update(UpdateMemberReq memberForm,String memberId){
    Member member = memberRepository.findById(memberForm.getMemberId())
            .orElseThrow(()->new UpdateMemberException("invalid member id","MEMBER-UPDATE-001"));

    member.update(memberForm,memberId);

    return UpdateMemberResult.of(member);
  }

  public NicknameDuplicate checkNickname(String nickname){
    List<Member> members = memberRepository.findMemberByNickname(nickname);

    return new NicknameDuplicate(nickname,nicknameIsUnique(members));
  }
  public boolean nicknameIsUnique(List<Member> list){
    return list.isEmpty();
  }

  public String getId(String loginMemberId){
    Member member = memberRepository.findById(loginMemberId)
          .orElseThrow(()->new UpdateMemberException("invalid member id","MEMBER-UPDATE-001"));
    return member.getId();
  }

  @Transactional
  public String renewPassword(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new RepositoryException("invalid member email","INVALID MEMBER"));
    return member.updateToRandomPassword();
  }

  @Transactional
  public Boolean exit(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new RepositoryException("invalid member id","INVALID MEMBER"));
    deleteThumbsByMemberId(memberId);
    deleteReviewsByMemberId(memberId);
    deleteReportsByMemberId(memberId);
    memberRepository.delete(member);
    return Boolean.TRUE;
  }
  public void deleteThumbsByMemberId(String memberId){
    thumbsRepository.deleteAllByMemberId(memberId);
  }
  public void deleteReviewsByMemberId(String memberId){
    reviewRepository.deleteAllByMemberId(memberId);
  }
  public void deleteReportsByMemberId(String memberId){
    reportRepository.deleteAllByMemberId(memberId);
  }
  public boolean validatePassword(ValidatePasswordRequest passwordRequest, String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new RepositoryException("invalid member id ["+memberId+"]","INVALID MEMBER"));
    return member.isCorrectPassword(passwordRequest.getPassword());
  }
}
