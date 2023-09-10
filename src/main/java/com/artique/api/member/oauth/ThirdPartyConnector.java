package com.artique.api.member.oauth;

import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.OauthExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

  public PublicKeyParams getPublicKeyParamsFromAppleServer(String kid){
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<String> requestMessage = new HttpEntity<>(httpHeaders);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<AppleJwks> response;
    try {
      response = restTemplate
              .exchange("https://appleid.apple.com/auth/keys",HttpMethod.GET,requestMessage,AppleJwks.class);
      AppleJwks jwks = response.getBody();
      if(jwks==null || jwks.getKeys().isEmpty())
        throw new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString());
      AppleJwk appleJwk = jwks.getKeys().stream().filter((AppleJwk a)->Objects.equals(a.getKid(),kid))
              .findFirst().orElseThrow(()->new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString()));
      return PublicKeyParams.of(appleJwk);
    }catch (Exception e){
      throw LoginException.builder().message("google jwks api exception")
              .errorCode(LoginExceptionCode.OAUTH_NETWORK_EXCEPTION.toString())
              .build();
    }
  }

  public PublicKeyParams getPublicKeyParamsFromGoogleServer(String kid){
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<String> requestMessage = new HttpEntity<>(httpHeaders);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<GoogleJwks> response;
    try {
      response = restTemplate
              .exchange("https://www.googleapis.com/oauth2/v3/certs",HttpMethod.GET,requestMessage,GoogleJwks.class);
      GoogleJwks jwks = response.getBody();
      if(jwks==null || jwks.getKeys().isEmpty())
        throw new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString());
      GoogleJwk googleJwk = jwks.getKeys().stream().filter((GoogleJwk j)-> Objects.equals(j.getKid(), kid))
              .findFirst().orElseThrow(()->new LoginException("invalid kid", OauthExceptionCode.INVALID_KID.toString()));
      return PublicKeyParams.of(googleJwk);
    }catch (Exception e){
      throw LoginException.builder().message("google jwks api exception")
              .errorCode(LoginExceptionCode.OAUTH_NETWORK_EXCEPTION.toString())
              .build();
    }
  }
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  private static class GoogleJwks{
    private List<GoogleJwk> keys;
  }
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  private static class AppleJwks{
    private List<AppleJwk> keys;
  }

}
