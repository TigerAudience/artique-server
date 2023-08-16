package com.artique.api.intertceptor;

import com.artique.api.member.exception.LoginErrorCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
      throw new LoginException("invalid session id during login", LoginErrorCode.INVALID_SESSION_ID.toString());
    }
    return HandlerInterceptor.super.preHandle(request,response,handler);
  }


  private String getSessionIdFromCookie(HttpServletRequest request){
    Cookie[] cookies = request.getCookies();
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
