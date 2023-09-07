package com.artique.api.member;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.exception.ExceptionController;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.request.JoinMemberReq;
import com.artique.api.member.request.LoginMemberReq;
import com.artique.api.member.request.OauthMemberReq;
import com.artique.api.member.response.JoinMember;
import com.artique.api.member.response.LoginMember;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
  @InjectMocks
  private MemberController memberController;
  @Mock
  private MemberService memberService;
  private MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();
  @BeforeEach
  public void init(){
    mockMvc = MockMvcBuilders.standaloneSetup(memberController)
            .setControllerAdvice(ExceptionController.class)
            .build();
  }
  @Test
  @DisplayName("duplicated 성공 테스트")
  void duplicated_success() throws Exception{
    // given
    when(memberService.checkDuplicateMember("sample_id")).thenReturn(true);

    // when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/member/duplicate").param("member-id","sample_id")
    );

    //then
    ResultActions mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("memberId","sample_id").exists());
  }

  @Test
  @DisplayName("duplicated 실패 테스트")
  void duplicated_failed()throws Exception{
    //given
    when(memberService.checkDuplicateMember("sample_id")).thenReturn(false);

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/member/duplicate").param("member-id","sample_id")
    );

    //then
    resultActions.andExpect(status().isForbidden())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);
              assertThat(errorResponse.getCode()).isEqualTo(LoginExceptionCode.DUPLICATE_LOGIN_ID.toString());
            });
  }


  @Test
  @DisplayName("join 성공 테스트")
  void join() throws Exception{
    //given
    when(memberService.checkDuplicateMember("sample_id")).thenReturn(true);
    JoinMemberReq request = new JoinMemberReq("sample_id","sample_pw");
    JoinMember expectResponse = new JoinMember("sample_id",null,null,null);
    when(memberService.join(any())).thenReturn(expectResponse);

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/member/join").content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
    );

    //then
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              JoinMember response = mapper.readValue(body, JoinMember.class);
              assertThat(response.getId()).isEqualTo(expectResponse.getId());
            });
  }

  @Test
  @DisplayName("join 실패 테스트, 중복 id")
  void join_failed_duplicate_id() throws Exception { //given
    when(memberService.checkDuplicateMember("sample_id")).thenReturn(false);
    JoinMemberReq request = new JoinMemberReq("sample_id","sample_pw");
    ErrorResponse expectResponse = new ErrorResponse("DUPLICATE_LOGIN_ID","duplicated member id");

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/member/join").content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
    );

    //then
    resultActions.andExpect(status().isForbidden())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              ErrorResponse errorResponse = mapper.readValue(body, ErrorResponse.class);
              assertThat(errorResponse.getCode()).isEqualTo(expectResponse.getCode());
            });
  }

  @Test
  @DisplayName("login 성공 테스트")
  void login_success() throws Exception {
    //given
    LoginMember expectResponse = new LoginMember(true,"sample_id");
    when(memberService.login(any(),any())).thenReturn(expectResponse);

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/member/login")
                    .content(mapper.writeValueAsString(new LoginMemberReq("sample_id",null)))
                    .contentType(MediaType.APPLICATION_JSON)
    );
    //then
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              LoginMember response = mapper.readValue(body, LoginMember.class);
              assertThat(response).usingRecursiveComparison().isEqualTo(expectResponse);
            });
  }

  @Test
  @DisplayName("oauth login 성공 테스트")
  void oauth_login() throws Exception{
    //given
    LoginMember expectResponse = new LoginMember(true,"kakao@sample_id");
    when(memberService.oauthLogin(any(),any())).thenReturn(expectResponse);

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/member/oauth")
                    .content(mapper.writeValueAsString(new OauthMemberReq("kakao","sample_token")))
                    .contentType(MediaType.APPLICATION_JSON)
    );
    //then
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              LoginMember response = mapper.readValue(body, LoginMember.class);
              assertThat(response).usingRecursiveComparison().isEqualTo(expectResponse);
            });
  }
}
