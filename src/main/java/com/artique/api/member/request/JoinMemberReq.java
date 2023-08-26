package com.artique.api.member.request;

import com.artique.api.entity.Member;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberReq {
  @Schema(description = "조회할 회원 ID",defaultValue = "null")
  @Parameter(required = true)
  @NotNull(message = "member id는 필수 입력값입니다")
  private String memberId;
  private String memberPW;

  public static Member toMember(JoinMemberReq joinMemberReq){
    return new Member(joinMemberReq.getMemberId(),
            "임시 닉네임", "임시 url","임시 소개", joinMemberReq.getMemberPW());
  }

}