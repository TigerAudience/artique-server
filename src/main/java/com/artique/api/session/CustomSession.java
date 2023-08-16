package com.artique.api.session;

import org.springframework.stereotype.Component;

@Component
public interface CustomSession {
  public String createSession(String memberId);
  public boolean validateSessionId(String id);
}
