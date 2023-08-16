package com.artique.api.member.response;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinMember {
  private String id;
  private String nickname;
  private String profileUrl;
  private String introduce;
  private String cookie;

  public static JoinMember of(Member member){
    return new JoinMember(member.getId(),member.getNickname(),member.getProfileUrl(),member.getIntroduce(),null);
  }

  public void adjustCookie(String cookie){
    this.cookie=cookie;
  }
}
