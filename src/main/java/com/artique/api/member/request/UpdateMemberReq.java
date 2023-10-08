package com.artique.api.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberReq {
  private String memberId;
  private String nickname;
  private String profileUrl;
  private String introduce;
  private String password;
}
