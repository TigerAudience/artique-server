package com.artique.api.member.oauth;

import com.artique.api.member.dto.KakaoUser;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoProvider implements OauthProvider{
  private final ThirdPartyConnector connector;
  @Override
  public OauthMember getUserFromThirdParty(String accessToken) {
    String userString = getUserByString(accessToken);
    return OauthMember.of(parseString(userString));
  }
  private String getUserByString(String accessToken){

    String url = "https://kapi.kakao.com/v2/user/me";

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders.setBearerAuth(accessToken);
    HttpEntity<String> requestMessage = new HttpEntity<>(httpHeaders);

    return connector.getUserByString(url, requestMessage);
  }
  private KakaoUser parseString(String userString){
    KakaoUser parseUser;
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
    try {
      parseUser = objectMapper.readValue(userString, KakaoUser.class);
    }catch (Exception e){
      throw LoginException.builder()
              .message("parsing failed while mapping kakao user")
              .errorCode(LoginExceptionCode.PARSE_MEMBER_FAIL.toString())
              .build();
    }
    return parseUser;
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return thirdParty.equals("kakao");
  }
}
