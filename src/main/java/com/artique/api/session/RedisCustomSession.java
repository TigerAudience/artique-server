package com.artique.api.session;

import com.artique.api.entity.Member;
import com.artique.api.entity.redis.RedisSession;
import com.artique.api.redis.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RedisCustomSession implements CustomSession{
  private final SessionRepository sessionRepository;
  @Override
  public String createSession(Member member) {
    RedisSession session = sessionRepository.save(new RedisSession(member));
    return session.getId();
  }

  @Override
  public boolean validateSessionId(String sessionId) {
    RedisSession session = null;
    try {
       session = sessionRepository.findById(sessionId)
              .orElseThrow(()->new RuntimeException("null"));
    }catch (RuntimeException e){
      return false;
    }
    if(session==null)
      return false;
    session.renew();
    return true;
  }

  @Override
  public String getMemberId(String sessionId) {
    try {
      return sessionRepository.findById(sessionId).orElseThrow(()->new RuntimeException("invalid session id"))
              .getMemberId();
    }catch (Exception e){
      return null;
    }
  }
}
