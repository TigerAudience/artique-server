package com.artique.api.member.exception;

import lombok.Getter;

@Getter
public enum LoginErrorCode {
  DUPLICATE_LOGIN_ID("ACCOUNT-001");

  LoginErrorCode(String status){
  }
}
