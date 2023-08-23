package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.dto.KakaoUser;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.member.response.MemberDuplicate;
import com.artique.api.session.CustomSession;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
      throw new LoginException("member id duplicate exception", LoginExceptionCode.DUPLICATE_LOGIN_ID.toString());
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
  public LoginMember oauthLogin(OauthMemberReq memberReq, HttpServletResponse httpResponse) throws Exception{
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://kapi.kakao.com/v2/user/me";

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders.setBearerAuth(memberReq.getAccessToken());
    HttpEntity<?> requestMessage = new HttpEntity<>(httpHeaders);

    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,requestMessage,String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
    KakaoUser kakaoUser = objectMapper.readValue(response.getBody(), KakaoUser.class);

    OauthMember oauthMember = OauthMember.of(kakaoUser);
    JoinMemberReq joinMemberReq = OauthMember.toJoinMemberReq(oauthMember);

    Optional<Member> member = memberRepository.findById(joinMemberReq.getMemberId());
    if(member.isEmpty())
      join(joinMemberReq);

    Member findMember = memberRepository.findById(joinMemberReq.getMemberId()).orElseThrow();

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
