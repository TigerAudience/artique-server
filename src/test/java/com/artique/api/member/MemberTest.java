package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberTest {

  @InjectMocks
  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private CustomSession session;

  @Test
  @DisplayName("중복 없는 아이디 테스트")
  void validateId(){
    //given
    String memberId = "sample_member_id";
    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    //when
    boolean isValid  = memberService.checkDuplicateMember(memberId);

    //then
    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("증복 아이디 테스트")
  void validateId_duplicate(){
    //given
    String memberId = "sample_member_id";
    Member initMember = new Member(memberId,"sample_nickname","sample_profileUrl",
            "sample_introduce","sample_password", ZonedDateTime.now());
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(initMember));

    //when
    boolean isValid = memberService.checkDuplicateMember(memberId);
    //then
    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("회원 가입 테스트")
  void join(){
    //given
    JoinMemberReq joinMemberReq = new JoinMemberReq("sample_id","sample_password");
    Member member = new Member(joinMemberReq.getMemberId(),"임시 닉네임", "임시 url",
            "임시 소개", joinMemberReq.getMemberPW(),ZonedDateTime.now());
    when(memberRepository.save(any(Member.class))).thenReturn(member);
    JoinMember joinMember = new JoinMember(member.getId(),member.getNickname(),
            member.getProfileUrl(),member.getIntroduce());

    //when
    JoinMember result = memberService.join(joinMemberReq);

    //then
    assertThat(result).usingRecursiveComparison().isEqualTo(joinMember);
  }

  @Test
  @DisplayName("로그인 성공 테스트")
  void login_success(){
    //given
    LoginMemberReq memberReq = new LoginMemberReq("sample_id","sample_password");
    Member member = new Member(memberReq.getMemberId(),"sample_nickname","sample_profile_url"
            ,"sample_intro",memberReq.getMemberPW(),ZonedDateTime.now());
    MockHttpServletResponse response = new MockHttpServletResponse();
    String sampleSessionId = UUID.randomUUID().toString();
    when(session.createSession(member)).thenReturn(sampleSessionId);
    when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

    //when
    LoginMember loginMember = memberService.login(memberReq,response);

    //then
    Cookie cookie = new Cookie("session-id",sampleSessionId);
    LoginMember resultLoginMember = new LoginMember(true,member.getId());
    assertThat(loginMember).usingRecursiveComparison().isEqualTo(resultLoginMember);
    assertThat(response.getCookie("session-id")).usingRecursiveComparison().isEqualTo(cookie);
  }

  @Test
  @DisplayName("로그인 실패 테스트 [존재하지 않는 id일 때]")
  void login_invalid_memberId(){
    //given
    LoginMemberReq memberReq = new LoginMemberReq("sample_id","sample_password");
    MockHttpServletResponse response = new MockHttpServletResponse();

    when(memberRepository.findById(memberReq.getMemberId())).thenReturn(Optional.empty());

    //when
    LoginException exception = Assertions.assertThrows(LoginException.class,
            ()->memberService.login(memberReq,response));

    //then
    LoginExceptionCode code = LoginExceptionCode.INVALID_MEMBER_ID;
    assertThat(exception.getErrorCode()).isEqualTo(code.toString());
  }

  @Test
  @DisplayName("로그인 실패 테스트 [아이디 & 비밀번호가 데이터베이스 정보와 일치하지 않을 때]")
  void login_invalid_password(){
    //given
    LoginMemberReq memberReq = new LoginMemberReq("sample_id","wrong_password");
    MockHttpServletResponse response = new MockHttpServletResponse();
    Member dbMember = new Member(memberReq.getMemberId(),"sample_nickname","sample_profile_url"
            ,"sample_intro","sample_password",ZonedDateTime.now());
    when(memberRepository.findById(memberReq.getMemberId())).thenReturn(Optional.of(dbMember));

    //when
    LoginException exception = Assertions.assertThrows(LoginException.class,
            ()->memberService.login(memberReq,response));

    //then
    LoginExceptionCode code = LoginExceptionCode.INVALID_PASSWORD;
    assertThat(exception.getErrorCode()).isEqualTo(code.toString());
  }
}
