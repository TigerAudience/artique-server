package com.artique.api.interceptor;

import com.artique.api.exception.ErrorResponse;
import com.artique.api.exception.ExceptionController;
import com.artique.api.intertceptor.CookieAuthorizationInterceptor;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.session.CustomSession;
import com.artique.api.test.interceptor.InterceptorTestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CookieAuthorizationInterceptorTest {

  @InjectMocks
  @Spy
  private CookieAuthorizationInterceptor interceptor;
  @Mock
  private CustomSession session;

  @Spy
  private InterceptorTestController testController;

  private MockMvc mockMvc;
  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void init(){
    mockMvc = MockMvcBuilders.standaloneSetup(testController)
            .addInterceptors(interceptor)
            .setControllerAdvice(ExceptionController.class)
            .build();
  }

  @Test
  @DisplayName("preHandle 성공 테스트")
  void preHandle_success()throws Exception{
    //given
    when(session.validateSessionId(anyString())).thenReturn(true);

    //when
    ResultActions resultActions = mockMvc.perform(
            get("/test/interceptor")
                    .cookie(new Cookie("session-id","sample-id"))
    );

    //then
    verify(interceptor,times(1)).preHandle(any(),any(),any());
    verify(testController,times(1)).testInterceptor();
    resultActions.andExpect(status().isOk());
  }
  @Test
  @DisplayName("preHandle 실패 테스트 [invalid session id값일 때]")
  void preHandle_failed_invalid_sessionId()throws Exception{
    //given
    when(session.validateSessionId(anyString())).thenReturn(false);

    //when
    ResultActions resultActions = mockMvc.perform(
            get("/test/interceptor")
                    .cookie(new Cookie("session-id","invalid value"))
    );

    //then
    verify(interceptor,times(1)).preHandle(any(),any(),any());
    verify(session,times(1)).validateSessionId(anyString());
    verify(testController,never()).testInterceptor();
    resultActions.andExpect(status().isForbidden())
            .andExpect((r)->{
              String body = r.getResponse().getContentAsString();
              ErrorResponse response = mapper.readValue(body, ErrorResponse.class);
              Assertions.assertThat(response.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.toString());
            });
  }

  @Test
  @DisplayName("preHandle 실패 테스트 [쿠키값이 완전히 없을 때]")
  void preHandle_failed_empty_cookies()throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(
            get("/test/interceptor")
    );

    //then
    verify(interceptor, times(1)).preHandle(any(),any(),any());
    verify(session,never()).validateSessionId(anyString());
    verify(testController,never()).testInterceptor();
    resultActions.andExpect(status().isForbidden())
            .andExpect((r)->{
              String body = r.getResponse().getContentAsString();
              ErrorResponse response = mapper.readValue(body, ErrorResponse.class);
              Assertions.assertThat(response.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.toString());
            });
  }

  @Test
  @DisplayName("preHandle 실패 테스트 [cookie값들은 있지만, session-id 쿠키값 없을 때]")
  void preHandle_failed_no_sessionId_cookie()throws Exception{
    //given

    //when
    ResultActions resultActions = mockMvc.perform(
            get("/test/interceptor")
                    .cookie(new Cookie("not-session-id","sample-value"))
    );

    //then
    verify(interceptor, times(1)).preHandle(any(),any(),any());
    verify(session,never()).validateSessionId(anyString());
    verify(testController,never()).testInterceptor();
    resultActions.andExpect(status().isForbidden())
            .andExpect((r)->{
              String body = r.getResponse().getContentAsString();
              ErrorResponse response = mapper.readValue(body, ErrorResponse.class);
              Assertions.assertThat(response.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.toString());
            });
  }
}
