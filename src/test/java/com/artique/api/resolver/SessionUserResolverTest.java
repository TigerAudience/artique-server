package com.artique.api.resolver;

import com.artique.api.exception.ExceptionController;
import com.artique.api.session.CustomSession;
import com.artique.api.test.resolver.ResolverTestController;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SessionUserResolverTest {
  @InjectMocks
  @Spy
  private SessionUserResolver sessionUserResolver;

  @Spy
  private ResolverTestController testController;

  @Mock
  private CustomSession customSession;
  private MockMvc mockMvc;

  @BeforeEach
  public void init(){
    mockMvc = MockMvcBuilders.standaloneSetup(testController)
            .setCustomArgumentResolvers(sessionUserResolver)
            .setControllerAdvice(ExceptionController.class)
            .build();
  }

  @Test
  @DisplayName("resolveArgument 성공 테스트")
  void login_user_resolve_success() throws Exception{
    //given
    when(customSession.validateSessionId(any())).thenReturn(true);
    when(customSession.getMemberId(any())).thenReturn("sample-id");

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/test/resolver")
                    .cookie(new Cookie("session-id","sample-value"))
    );

    //then
    String expectResult = "sample-id";
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              Assertions.assertThat(body).isEqualTo(expectResult);
            });
  }
  @Test
  @DisplayName("resolveArgument 실패 테스트 [cookie 값 없을 때]")
  void login_user_resolve_failed_no_cookie() throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/test/resolver")
    );

    //then
    verify(customSession,never()).validateSessionId(any());
    verify(customSession,never()).getMemberId(any());
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              Assertions.assertThat(body).isEmpty();
            });
  }
  @Test
  @DisplayName("resolveArgument 실패 테스트 [cookie값은 있지만, session-id값 없을 때]")
  void login_user_resolve_failed_no_sessionId_cookie() throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/test/resolver")
                    .cookie(new Cookie("not-session","sample-value"))
    );

    //then
    verify(customSession,never()).validateSessionId(any());
    verify(customSession,never()).getMemberId(any());
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              Assertions.assertThat(body).isEmpty();
            });
  }
  @Test
  @DisplayName("resolveArgument 실패 테스트 [invalid session id일 때]")
  void login_user_resolve_failed_invalid_sessionId() throws Exception{
    //given
    when(customSession.validateSessionId(any())).thenReturn(false);

    //when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/test/resolver")
                    .cookie(new Cookie("session-id","sample-value"))
    );

    //then
    verify(customSession,never()).getMemberId(any());
    verify(customSession,Mockito.times(1)).validateSessionId(any());
    resultActions.andExpect(status().isOk())
            .andExpect((MvcResult result) -> {
              String body = result.getResponse().getContentAsString();
              Assertions.assertThat(body).isEmpty();
            });
  }

  @Test
  @DisplayName("supportsParameter 성공 테스트")
  void supportParameter_success() throws Exception{
    //given
    when(customSession.validateSessionId(any())).thenReturn(true);
    when(customSession.getMemberId(any())).thenReturn("sample-id");

    //when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/test/resolver")
            .cookie(new Cookie("session-id","sample-value")));

    //then
    resultActions.andExpect(status().isOk());
  }

  @Test
  @DisplayName("supportsParameter 실패 테스트 [parameter가 @LoginUser 어노테이션을 가지고 있지 않을 때]")
  void supportParameter_failed_annotation_not_exist()throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .get("/test/resolver/supports-parameter/annotation"));

    //then
    verify(sessionUserResolver,times(1)).supportsParameter(any());
    verify(sessionUserResolver,never()).resolveArgument(any(),any(),any(),any());
  }

  @Test
  @DisplayName("supportsParameter 실패 테스트 [parameter가 String타입이 아닐 때]")
  void supportParameter_failed_parameterType_not_String() throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .get("/test/resolver/supports-parameter/type"));
    //then
    verify(sessionUserResolver,times(1)).supportsParameter(any());
    verify(sessionUserResolver,never()).resolveArgument(any(),any(),any(),any());
  }

}
