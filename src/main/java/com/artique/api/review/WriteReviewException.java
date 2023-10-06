package com.artique.api.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WriteReviewException extends RuntimeException{
  private String message;
  private String errorCode;
}
