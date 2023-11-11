package com.artique.api.exception.global;

import lombok.Getter;

@Getter
public class InterceptorException extends RuntimeException{
  private final String interceptorName;
  public InterceptorException(String interceptorName,String message){
    super(message);
    this.interceptorName=interceptorName;
  }
}
