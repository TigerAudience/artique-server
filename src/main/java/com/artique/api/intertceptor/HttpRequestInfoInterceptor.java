package com.artique.api.intertceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class HttpRequestInfoInterceptor implements HandlerInterceptor {
  private final HttpRequestRepository httpRequestRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    httpRequestRepository.save(request);
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    httpRequestRepository.delete();
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
