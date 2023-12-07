package com.artique.api.entity.redis;

import com.artique.api.entity.Member;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.ZonedDateTime;
import java.util.UUID;

@RedisHash("session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedisSession {
  @Id
  private String id;
  private String memberId;
  @TimeToLive
  private Long expirationInSeconds;
  public RedisSession(Member member){
    this.id=UUID.randomUUID().toString();
    this.memberId=member.getId();
    this.expirationInSeconds=maxTime;
  }
  public void renew(){
    this.expirationInSeconds=maxTime;
  }
  private static final Long maxTime=60L;
}
