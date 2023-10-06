package com.artique.api.member.response;

import com.artique.api.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NicknameDuplicate {
  private String nickName;
  private boolean isUnique;

  public static NicknameDuplicate of(Optional<Member> member,String requestNickname){
    boolean isUnique = member.isEmpty();
    return new NicknameDuplicate(requestNickname,isUnique);
  }
}
