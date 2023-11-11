package com.artique.api.exception;

import com.artique.api.exception.global.InterceptorException;
import com.artique.api.feed.FeedException;
import com.artique.api.intertceptor.HttpPretty;
import com.artique.api.intertceptor.HttpRequestRepository;
import com.artique.api.member.exception.LoginException;
import com.artique.api.musical.MusicalException;
import com.artique.api.thumbs.ThumbsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
  private final HttpRequestRepository httpRequestRepository;

  @ExceptionHandler(LoginException.class)
  public ResponseEntity<ErrorResponse> loginException(LoginException e) throws IOException {
    HttpPretty httpPretty = httpRequestRepository.getPrettyString();
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage())
            .httpRequest(httpPretty).build(),
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

  @ExceptionHandler(ThumbsException.class)
  public ResponseEntity<ErrorResponse> musicalException(ThumbsException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> methodArgumentTypeException(MethodArgumentTypeMismatchException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getErrorCode()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InterceptorException.class)
  public ResponseEntity<ErrorResponse> interceptor(InterceptorException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getInterceptorName()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }
}
