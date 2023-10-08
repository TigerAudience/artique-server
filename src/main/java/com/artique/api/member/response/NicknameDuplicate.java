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
}
