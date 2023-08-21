package com.artique.api.session;

import com.artique.api.entity.Member;
import org.springframework.stereotype.Component;

@Component
public interface CustomSession {
  public String createSession(Member member);
  public boolean validateSessionId(String id);
  public String getMemberId(String sessionId);
}
