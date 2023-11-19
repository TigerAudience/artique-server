package com.artique.api.member.request;

import com.artique.api.entity.Member;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberReq {
  @Schema(description = "조회할 회원 ID")
  @Parameter(required = true)
  @NotNull(message = "member id는 필수 입력값입니다")
  private String memberId;
  private String memberPW;
}
