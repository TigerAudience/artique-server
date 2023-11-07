package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.oauth.OauthService;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.LoginMember;
import com.artique.api.session.CustomSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @InjectMocks
  @Spy
  private MemberService memberService;

  @Mock
  private CustomSession session;
  @Mock
  private MemberRepository memberRepository;
  @Mock
  private OauthService oauthService;

  @Test
  @DisplayName("oauthLogin 성공 [member id db에 없을 때]")
  void oauthLogin_success(){
    //given
    OauthMember oauthMember = new OauthMember("sample-id","sample-third-party");
    OauthMemberReq request = new OauthMemberReq("sample-third-party","sample-token");
    MockHttpServletResponse response = new MockHttpServletResponse();

    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    Member member = new Member(oauthMember.getId(), "임시 닉네임",
            "임시 url","임시 소개","임시 password", 0, null,
            dateTime);
    when(oauthService.getOauthMember(anyString(), anyString()))
            .thenReturn(oauthMember);
    doReturn(false)
            .when(memberService).checkDuplicateMember(anyString());
    when(memberRepository.findById(anyString())).thenReturn(Optional.of(member));
    when(session.createSession(any())).thenReturn("sample-session-id");
    //when
    LoginMember responseMember = memberService.oauthLogin(request,response);

    //then
    LoginMember expectResult = new LoginMember(true, oauthMember.getId());
    verify(oauthService,times(1)).getOauthMember(anyString(),anyString());
    verify(memberService,times(1)).checkDuplicateMember(anyString());
    verify(memberRepository,times(1)).findById(anyString());
    verify(memberRepository,never()).save(any());
    verify(session,times(1)).createSession(any());
    assertThat(responseMember).usingRecursiveComparison().isEqualTo(expectResult);
    assertThat(Objects.requireNonNull(response.getCookie("session-id")).getValue()).isEqualTo("sample-session-id");
  }

  @Test
  @DisplayName("oauthLogin 성공 [member id db에 있을 때]")
  void oauthLogin_success_memberId_exist(){
    //given
    OauthMember oauthMember = new OauthMember("sample-id","sample-third-party");
    OauthMemberReq request = new OauthMemberReq("sample-third-party","sample-token");
    MockHttpServletResponse response = new MockHttpServletResponse();

    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    Member member = new Member(oauthMember.getId(), "임시 닉네임",
            "임시 url","임시 소개","임시 password",0,
            null, dateTime);
    when(oauthService.getOauthMember(anyString(), anyString()))
            .thenReturn(oauthMember);
    doReturn(true)
            .when(memberService).checkDuplicateMember(anyString());
    when(memberRepository.save(any())).thenReturn(member);
    when(session.createSession(any())).thenReturn("sample-session-id");
    //when
    LoginMember responseMember = memberService.oauthLogin(request,response);

    //then
    LoginMember expectResult = new LoginMember(true, oauthMember.getId());
    verify(oauthService,times(1)).getOauthMember(anyString(),anyString());
    verify(memberService,times(1)).checkDuplicateMember(anyString());
    verify(memberRepository,times(1)).save(any());
    verify(memberRepository,never()).findById(anyString());
    verify(session,times(1)).createSession(any());
    assertThat(responseMember).usingRecursiveComparison().isEqualTo(expectResult);
    assertThat(Objects.requireNonNull(response.getCookie("session-id")).getValue()).isEqualTo("sample-session-id");
  }
}
