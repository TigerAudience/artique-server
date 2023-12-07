package com.artique.api.session;

import com.artique.api.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class MemoryTimeSession implements CustomSession{
  private final SessionStore sessionStore;
  @Override
  public String createSession(Member member) {
    if(sessionStore.hasSession(member)) {
      Long sessionKey = sessionStore.getSessionKey(member);
      return sessionKey!=null ? sessionKey.toString() : null;
    }
    long sessionKey = sessionStore.init(member);
    return Long.toString(sessionKey);
  }

  @Override
  public boolean validateSessionId(String id) {
    long sessionKey;
    try {
      sessionKey=Long.parseLong(id);
    }catch (Exception e){
      return false;
    }
    return !sessionStore.sessionExpired(sessionKey);
  }

  @Override
  public String getMemberId(String sessionId) {
    long sessionKey;
    try {
      sessionKey=Long.parseLong(sessionId);
    }catch (Exception e){
      return null;
    }
    return sessionStore.getMemberWithSessionUpdate(sessionKey);
  }

}
