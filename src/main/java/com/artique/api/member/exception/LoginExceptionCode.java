package com.artique.api.member.exception;

import lombok.Getter;

@Getter
public enum LoginExceptionCode {
  DUPLICATE_LOGIN_ID("ACCOUNT-001"),INVALID_SESSION_ID("ACCOUNT-002"),
  INVALID_MEMBER_ID("ACCOUNT-003"),INVALID_PASSWORD("ACCOUNT-004"),
  CANT_FIND_OAUTH_MEMBER("ACCOUNT-005"),PARSE_MEMBER_FAIL("ACCOUNT-006"),
  OAUTH_NETWORK_EXCEPTION("ACCOUNT-007"),INVALID_THIRD_PARTY_NAME("ACCOUNT-008"),
  INVALID_JWT("ACCOUNT-009");

  LoginExceptionCode(String status){
  }
}
