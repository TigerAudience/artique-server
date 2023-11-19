package com.artique.api.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EmailRequest {
  @AllArgsConstructor
  @Getter
  public static class JoinAuthorizationRequest{
    private String mailAddress;
  }

  @AllArgsConstructor
  @Getter
  public static class VerificationRequest{
    private String email;
    private Integer code;
  }
}
