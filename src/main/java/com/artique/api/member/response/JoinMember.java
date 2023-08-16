package com.artique.api.member.response;

import com.artique.api.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinMember {
  @Schema(description = "회원 가입 할 ID")
  private String id;
  @Schema(description = "회원 가입 시 자동 생성된 닉네임")
  private String nickname;
  @Schema(description = "회원 가입 시 자동 생성된 프로필 url")
  private String profileUrl;
  @Schema(description = "회원 가입 시 자동 생성된 자기소개",defaultValue = "null")
  private String introduce;
  @Schema(description = "사용자 인증을 위한 쿠키값")
  private String cookie;

  public static JoinMember of(Member member){
    return new JoinMember(member.getId(),member.getNickname(),member.getProfileUrl(),member.getIntroduce(),null);
  }

  public void adjustCookie(String cookie){
    this.cookie=cookie;
  }
}
