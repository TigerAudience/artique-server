package com.artique.api.member.exception;

import lombok.Getter;

@Getter
public enum LoginErrorCode {
  DUPLICATE_LOGIN_ID("ACCOUNT-001"),INVALID_SESSION_ID("ACCOUNT-002")
  ,INVALID_MEMBER_ID("ACCOUNT-003"),INVALID_PASSWORD("ACCOUNT-004");

  LoginErrorCode(String status){
  }
}
