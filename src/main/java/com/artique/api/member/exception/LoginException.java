package com.artique.api.member.exception;

import com.artique.api.entity.Member;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class LoginException extends RuntimeException{
  private String message;
  private String errorCode;
}
