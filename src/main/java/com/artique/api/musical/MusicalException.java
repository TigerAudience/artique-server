package com.artique.api.musical;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MusicalException extends RuntimeException{
  private String message;
  private String errorCode;
}
