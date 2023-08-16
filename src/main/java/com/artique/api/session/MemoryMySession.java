package com.artique.api.session;

import com.artique.api.member.exception.LoginErrorCode;
import com.artique.api.member.exception.LoginException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryMySession implements CustomSession {

  private final ConcurrentHashMap<UUID, String> memorySession = new ConcurrentHashMap<>();

  @Override
  public String createSession(String memberId) {
    UUID sessionKey = UUID.randomUUID();
    memorySession.put(sessionKey,memberId);
    return sessionKey.toString();
  }

  @Override
  public boolean validateSessionId(String id) {
    String userId = memorySession.get(UUID.fromString(id));
    return !userId.isEmpty();
  }
}
