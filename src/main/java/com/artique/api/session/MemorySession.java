package com.artique.api.session;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemorySession implements CustomSession {

  private final ConcurrentHashMap<UUID, SessionValue> memorySession = new ConcurrentHashMap<>();

  @Override
  public String createSession(Member member) {
    return createWithMemberId(member);
  }
  private String createWithMemberId(Member member){
    String memberId = member.getId();
    UUID sessionKey = UUID.randomUUID();
    memorySession.put(sessionKey,new SessionValue(memberId));
    return sessionKey.toString();
  }

  @Override
  public boolean validateSessionId(String id) {
    SessionValue session = memorySession.get(UUID.fromString(id));
    if(session==null)
        return false;
    else{
      checkSessionExpire(session,id);
    }
    return true;
  }

  @Override
  public String getMemberId(String sessionId) {
    return memorySession.get(UUID.fromString(sessionId)).getMemberId();
  }

  private void checkSessionExpire(SessionValue session,String id){
    session.increaseCount();
    if(session.mustExpired())
        memorySession.remove(UUID.fromString(id));
  }

  @AllArgsConstructor
  @Getter
  private static class SessionValue{
    private String memberId;
    private Integer count;
    private Integer maxCount;

    public SessionValue(String memberId){
      this.memberId = memberId;
      this.count=0;
      this.maxCount=30;
    }
    public void increaseCount(){
      this.count+=1;
    }

    public boolean mustExpired(){
      return count >= maxCount;
    }
  }
}