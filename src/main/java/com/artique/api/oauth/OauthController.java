package com.artique.api.oauth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OauthController {
  @Value("${oauth.client-id}")
  private String clientId;
  @GetMapping("/oauth2/authorization/kakao")
  public String oauth(){
    return "redirect:https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
            +clientId+"&redirect_uri=http://localhost:8080/oauth";
  }
  @GetMapping("/oauth2/home")
  public String oauthHome(){
    return "index";
  }
  @GetMapping("/oauth")
  @ResponseBody
  public String getOauthToken(@RequestParam("code")String code) throws Exception{

    // 토큰 가져오기
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://kauth.kakao.com/oauth/token";

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type","authorization_code");
    body.add("client_id",clientId);
    body.add("redirect_uri","http://localhost:8080/oauth");
    body.add("code",code);
    HttpEntity<?> requestMessage = new HttpEntity<>(body,httpHeaders);

    HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage,String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

    KakaoToken kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);


    //사용자 정보 가져오기
    RestTemplate restTemplate2 = new RestTemplate();
    String url2 = "https://kapi.kakao.com/v2/user/me";

    HttpHeaders httpHeaders2 = new HttpHeaders();
    httpHeaders2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders2.setBearerAuth(kakaoToken.getAccess_token());
    HttpEntity<?> requestMessage2 = new HttpEntity<>(httpHeaders2);

    //Map<String, String> params = new HashMap<String, String>();
    //params.put("")

    ResponseEntity<String> response2 = restTemplate2.exchange(url2, HttpMethod.GET,requestMessage2,String.class);
    ObjectMapper objectMapper2 = new ObjectMapper();
    objectMapper2.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
    KakaoUser kakaoUser = objectMapper2.readValue(response2.getBody(), KakaoUser.class);

    return Long.toString(kakaoUser.getId());
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Setter
  @Getter
  private static class KakaoUser{
    private Long id;
    private String connected_at;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Setter
  @Getter
  private static class KakaoToken{
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;
  }
}
