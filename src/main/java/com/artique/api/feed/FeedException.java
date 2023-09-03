package com.artique.api.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FeedException extends RuntimeException{
  private String message;
  private String errorCode;
}
