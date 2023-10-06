package com.artique.api.profile.summary.response;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSummary {
  private String id;
  private String nickname;
  private String imageUrl;
  private String introduce;

  public static MemberSummary of(Member m){
    return new MemberSummary(m.getId(),m.getNickname(),m.getProfileUrl(),m.getIntroduce());
  }
}
