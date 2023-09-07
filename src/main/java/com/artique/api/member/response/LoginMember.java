package com.artique.api.member.response;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginMember {
  private boolean success;
  private String userId;

  public static LoginMember of(Member member){
    return new LoginMember(true,member.getId());
  }
}
