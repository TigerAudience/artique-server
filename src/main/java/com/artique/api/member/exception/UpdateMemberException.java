package com.artique.api.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberException extends RuntimeException{
  private String message;
  private String errorCode;
}
