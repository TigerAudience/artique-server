package com.artique.api.member.oauth;

import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class ThirdPartyConnector {

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

  @Value("${oauth.google.client-id}")
  private String clientId;
  @Value("${oauth.google.client-secret}")
  private String clientSecret;
  @Value("${oauth.google.redirect-uri}")
  private String redirectUri;
  @Value("${oauth.google.token-uri}")
  private String tokenUri;

  public String getGoogleAccessToken(String authorizationCode){
    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", authorizationCode);
    params.add("client_id", clientId);
    params.add("client_secret", clientSecret);
    params.add("redirect_uri", redirectUri);
    params.add("grant_type", "authorization_code");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<?> entity = new HttpEntity(params, headers);

    ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
    JsonNode accessTokenNode = responseNode.getBody();

    return accessTokenNode.get("access_token").asText();
  }
}
