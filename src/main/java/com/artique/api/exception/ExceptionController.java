package com.artique.api.exception;

import com.artique.api.member.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(LoginException.class)
  public ResponseEntity<ErrorResponse> loginException(LoginException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.FORBIDDEN);
  }
}
