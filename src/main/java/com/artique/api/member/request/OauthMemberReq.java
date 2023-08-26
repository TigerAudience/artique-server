package com.artique.api.member.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OauthMemberReq {
  @NotNull(message = "간편 로그인 기업은 필수 입력값입니다.")
  @Schema(description = "간편 로그인 할 third party 이름, 현재 지원하는 third party name : kakao, google (* 전부 소문자! *)")
  @Parameter(required = true)
  private String thirdPartyName;
  @NotNull(message = "token값은 필수 입력값입니다.")
  @Schema(description = "google의 경우에는 idToken값, kakao의 경우에는 accessToken 값")
  private String token;

}
