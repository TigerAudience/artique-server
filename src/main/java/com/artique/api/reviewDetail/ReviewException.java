package com.artique.api.reviewDetail;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewException extends RuntimeException{
  private String message;
  private String errorCode;
}
