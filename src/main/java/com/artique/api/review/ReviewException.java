package com.artique.api.review;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewException extends RuntimeException{
  private String message;
  private String errorCode;
}
