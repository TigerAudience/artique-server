package com.artique.api.exception;

import com.artique.api.feed.FeedException;
import com.artique.api.member.exception.LoginException;
import com.artique.api.musical.MusicalException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(LoginException.class)
  public ResponseEntity<ErrorResponse> loginException(LoginException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(FeedException.class)
  public ResponseEntity<ErrorResponse> feedException(FeedException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(MusicalException.class)
  public ResponseEntity<ErrorResponse> musicalException(MusicalException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> methodArgumentTypeException(MethodArgumentTypeMismatchException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }
}
