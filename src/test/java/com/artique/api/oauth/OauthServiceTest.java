package com.artique.api.oauth;

import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.oauth.GoogleProvider;
import com.artique.api.member.oauth.KakaoProvider;
import com.artique.api.member.oauth.OauthProvider;
import com.artique.api.member.oauth.OauthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OauthServiceTest {
  private OauthService oauthService;
  @Mock
  private GoogleProvider googleProvider;
  @Mock
  private KakaoProvider kakaoProvider;
  @BeforeEach
  void initProviders(){
    // List형태로 mocking 주입이 안됨.
    List<OauthProvider> providerList = new ArrayList<>();
    providerList.add(googleProvider);
    providerList.add(kakaoProvider);
    oauthService = new OauthService(providerList);
  }

  @Test
  @DisplayName("kakaoProvider 성공 테스트")
  void success_kakaoProvider(){
    //given
    lenient().when(kakaoProvider.canSupport("kakao")).thenReturn(true);
    lenient().when(googleProvider.canSupport(any())).thenReturn(false);
    OauthMember oauthMember = new OauthMember("kakao_sample_id","kakao");
    when(kakaoProvider.getUserFromThirdParty(any())).thenReturn(oauthMember);

    //when
    OauthMember getMember = oauthService.getOauthMember("kakao","sample_token");

    //then
    Assertions.assertThat(getMember).usingRecursiveComparison().isEqualTo(oauthMember);
  }
  @Test
  @DisplayName("googleProvider 성공 테스트")
  void success_googleProvider(){
    //given
    lenient().when(kakaoProvider.canSupport(any())).thenReturn(false);
    lenient().when(googleProvider.canSupport("google")).thenReturn(true);
    OauthMember oauthMember = new OauthMember("google_sample_id","google");
    when(googleProvider.getUserFromThirdParty(any())).thenReturn(oauthMember);

    //when
    OauthMember getMember = oauthService.getOauthMember("google","sample_token");

    //then
    Assertions.assertThat(getMember).usingRecursiveComparison().isEqualTo(oauthMember);
  }

  @Test
  @DisplayName("oauth login 실패 테스트 [미지원 third party]")
  void fail_oauth_login(){
    //given
    when(kakaoProvider.canSupport(any())).thenReturn(false);
    when(googleProvider.canSupport(any())).thenReturn(false);

    //when
    LoginException exception = assertThrows(LoginException.class,
            ()->oauthService.getOauthMember("empty","invalidToken"));
    //then
    LoginExceptionCode code = LoginExceptionCode.INVALID_THIRD_PARTY_NAME;
    assertThat(exception.getErrorCode()).isEqualTo(code.toString());

  }

}
