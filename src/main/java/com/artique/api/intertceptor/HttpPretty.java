package com.artique.api.intertceptor;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HttpPretty {
  private String uri;
  private String queryString;
  private Map<String,String> headers;
  private String body;
  public static HttpPretty of(HttpServletRequest request)throws IOException {
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();
    Map<String,String> headers = new HashMap<>();
    Iterator<String> iterator = request.getHeaderNames().asIterator();
    while(iterator.hasNext()){
      String name = iterator.next();
      headers.put(name,request.getHeader(name));
    }
    ServletInputStream inputStream = request.getInputStream();  // message body의 내용을 바이트코드로 읽어온다.
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트코드(UTF_8 인코딩) -> 문자열
    return new HttpPretty(uri,queryString,headers,messageBody);
  }
}
