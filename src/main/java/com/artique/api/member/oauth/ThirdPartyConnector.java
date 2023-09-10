package com.artique.api.member.oauth;

import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.OauthExceptionCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ThirdPartyConnector {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public String getUserByString(String url, HttpEntity<String> requestMessage){
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, requestMessage, String.class);
    }catch (Exception e){
      throw LoginException.builder().message("third party server connecting exception")
              .errorCode(LoginExceptionCode.OAUTH_NETWORK_EXCEPTION.toString())
              .build();
    }
    return response.getBody();
  }

  public PublicKeyParams getPublicKeyParamsFromGoogleServer(String kid){
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<String> requestMessage = new HttpEntity<>(httpHeaders);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Jwks> response;
    try {
      response = restTemplate
              .exchange("https://www.googleapis.com/oauth2/v3/certs",HttpMethod.GET,requestMessage,Jwks.class);
      Jwks jwks = response.getBody();
      if(jwks==null || jwks.getKeys().isEmpty())
        throw new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString());
      Jwk jwk = jwks.getKeys().stream().filter((Jwk j)-> Objects.equals(j.getKid(), kid))
              .findFirst().orElseThrow(()->new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString()));
      return PublicKeyParams.of(jwk);
    }catch (Exception e){
      throw LoginException.builder().message("google jwks api exception")
              .errorCode(LoginExceptionCode.OAUTH_NETWORK_EXCEPTION.toString())
              .build();
    }
  }
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  private static class Jwks{
    private List<Jwk> keys;
  }

}
