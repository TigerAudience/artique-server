package com.artique.api.member.exception;

import lombok.Getter;

@Getter
public enum LoginErrorCode {
  DUPLICATE_LOGIN_ID("ACCOUNT-001"),INVALID_SESSION_ID("ACCOUNT-002");

  LoginErrorCode(String status){
  }
}
