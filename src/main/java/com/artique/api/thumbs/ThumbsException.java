package com.artique.api.thumbs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ThumbsException extends RuntimeException{
  private String message;
  private String errorCode;
}
