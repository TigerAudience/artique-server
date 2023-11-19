package com.artique.api.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailException extends RuntimeException{
  private String message;
  private String code;
}
