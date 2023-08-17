package com.artique.api.member.exception;

import lombok.Getter;

@Getter
public enum LoginExceptionCode {
  DUPLICATE_LOGIN_ID("ACCOUNT-001"),INVALID_SESSION_ID("ACCOUNT-002")
  ,INVALID_MEMBER_ID("ACCOUNT-003"),INVALID_PASSWORD("ACCOUNT-004");

  LoginExceptionCode(String status){
  }
}
