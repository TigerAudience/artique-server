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

}
