package com.artique.api.member.request;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberReq {
  private String memberId;

  public static Member toMember(JoinMemberReq joinMemberReq){
    return new Member(joinMemberReq.getMemberId(),
            "임시 닉네임", "임시 url","임시 소개");
  }

}
