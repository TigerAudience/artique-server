package com.artique.api.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailRequest {
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class JoinAuthorizationRequest{
    private String mailAddress;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class VerificationRequest{
    private String email;
    private Integer code;
  }
}
