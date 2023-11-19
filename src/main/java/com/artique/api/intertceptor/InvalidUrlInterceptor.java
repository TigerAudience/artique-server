package com.artique.api.intertceptor;

import com.artique.api.exception.global.InterceptorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
public class InvalidUrlInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if(!(handler instanceof HandlerMethod)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 응답 상태 코드 설정
      response.setContentType("application/json");  // 응답의 Content-Type 설정
      response.getWriter().write( "invalid url : " + request.getRequestURI()+", method : "+request.getMethod());
      return false;
    }
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
