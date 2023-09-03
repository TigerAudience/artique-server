package com.artique.api.resolver;

import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionUserResolver implements HandlerMethodArgumentResolver {
  private final CustomSession session;
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginUser.class) && parameter.getParameterType().equals(String.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    return session.getMemberId(resolveSessionId(request));
  }
  private String resolveSessionId(HttpServletRequest request){
    return getSessionIdFromCookie(request);
  }
  private String getSessionIdFromCookie(HttpServletRequest request){
    Cookie[] cookies = request.getCookies();
    String sessionId = null;
    if(cookies==null)
      return null;
    for(Cookie cookie: cookies) {
      if(cookie.getName().equals("session-id")) {
        sessionId = cookie.getValue();
        break;
      }
    }
    return sessionId;
  }
}
