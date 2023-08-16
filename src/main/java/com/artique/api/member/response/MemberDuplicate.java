package com.artique.api.member.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDuplicate {
  @Schema(description = "중복 되지 않은 회원 ID")
  private String memberId;
}
