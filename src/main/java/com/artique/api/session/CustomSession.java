package com.artique.api.session;

import com.artique.api.entity.Member;
import org.springframework.stereotype.Component;

@Component
public interface CustomSession {
  String createSession(Member member);
  boolean validateSessionId(String id);
  String getMemberId(String sessionId);
}
