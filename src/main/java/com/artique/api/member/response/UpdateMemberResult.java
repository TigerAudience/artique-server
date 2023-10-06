package com.artique.api.member.response;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberResult {
  private String memberId;
  private boolean success;
  public static UpdateMemberResult of(Member member){
    return new UpdateMemberResult(member.getId(),true);
  }
}
