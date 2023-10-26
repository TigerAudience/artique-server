package com.artique.api.exception;

import com.artique.api.intertceptor.HttpPretty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String code;
  private String message;
  private HttpPretty httpRequest;
  public ErrorResponse(String code,String message){
    this.code=code;
    this.message=message;
    this.httpRequest=null;
  }
}
