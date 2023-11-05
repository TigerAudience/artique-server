package com.artique.api.intertceptor;

import com.artique.api.member.exception.LoginException;
import com.artique.api.session.CustomSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthorizationHeaderInterceptor implements HandlerInterceptor {
  private final CustomSession session;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if(request.getMethod().equals("OPTIONS"))
      return HandlerInterceptor.super.preHandle(request, response, handler);
    String sessionId = getSessionIdFromAuthorizationHeader(request);
    if(sessionId == null || !session.validateSessionId(sessionId)){
      throw new LoginException("authorization failure", HttpStatus.UNAUTHORIZED.toString());
    }
    return HandlerInterceptor.super.preHandle(request,response,handler);
  }
  private String getSessionIdFromAuthorizationHeader(HttpServletRequest request){
    String authorizationHeader = request.getHeader("Authorization");
    if(authorizationHeader==null)
      return null;
    return parseSessionId(authorizationHeader);
  }
  private String parseSessionId(String rawSessionId){
    return rawSessionId.replaceAll("session-id=","");
  }
}
