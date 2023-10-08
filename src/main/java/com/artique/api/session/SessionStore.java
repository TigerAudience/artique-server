package com.artique.api.session;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@AllArgsConstructor
@Component
public class SessionStore{
  private final HashMap<Long, SessionValue> memorySession = new HashMap<>();
  private final int durationDay=30;

  public long init(Member member){
    long sessionKey = memberToSessionKey(member);
    memorySession.put(sessionKey, SessionValue.initSessionValue(member.getId(),durationDay));
    return sessionKey;
  }

  public boolean hasSession(Member member){
    long sessionKey = memberToSessionKey(member);
    return memorySession.containsKey(sessionKey);
  }
  public Long getSessionKey(Member member){
    long sessionKey = memberToSessionKey(member);
    SessionValue sessionValue = memorySession.get(sessionKey);
    return sessionValue!=null ? sessionKey : null;
  }

  public String getMemberWithSessionUpdate(long sessionKey){
    SessionValue sessionValue = memorySession.get(sessionKey);
    if(sessionValue==null)
      return null;
    sessionValue.updateUsed();
    return sessionValue.getMemberId();
  }
  public boolean sessionExpired(long sessionKey){
    if(!memorySession.containsKey(sessionKey))
      return true;
    SessionValue session = memorySession.get(sessionKey);
    return session.expired();
  }

  private long memberToSessionKey(Member member){
    String memberId = member.getId();
    return memberId.hashCode();
  }

  @AllArgsConstructor
  @Getter
  private static class SessionValue{
    private LocalDateTime createdAt;
    private LocalDateTime lastUsed;
    private LocalDateTime expiredAt;
    private int duration;

    private String memberId;

    public static SessionValue initSessionValue(String memberId,int durationDay){
      LocalDateTime present = LocalDateTime.now();
      return new SessionValue(present,present,present.plusDays(durationDay)
              ,durationDay,memberId);
    }
    public void updateUsed(){
      this.lastUsed=LocalDateTime.now();
      this.expiredAt=this.lastUsed.plusDays(duration);
    }
    public boolean expired(){
      LocalDateTime present = LocalDateTime.now();
      return present.isAfter(expiredAt);
    }
  }
}