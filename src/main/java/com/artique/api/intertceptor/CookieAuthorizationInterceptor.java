package com.artique.api.intertceptor;

import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CookieAuthorizationInterceptor implements HandlerInterceptor {

  private final CustomSession session;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    if(request.getMethod().equals("OPTIONS"))
      return HandlerInterceptor.super.preHandle(request, response, handler);
    String sessionId = getSessionIdFromCookie(request);
    if(sessionId == null || !session.validateSessionId(sessionId)){
      throw new LoginException("authorization failure", HttpStatus.UNAUTHORIZED.toString());
    }
    return HandlerInterceptor.super.preHandle(request,response,handler);
  }


  private String getSessionIdFromCookie(HttpServletRequest request){
    Cookie[] cookies = request.getCookies();
    if(cookies==null)
      return null;
    String sessionId = null;
    for(Cookie cookie: cookies) {
      if(cookie.getName().equals("session-id")) {
        sessionId = cookie.getValue();
        break;
      }
    }
    return sessionId;
  }
}
