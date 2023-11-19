package com.artique.api.exception;

import com.artique.api.exception.global.InterceptorException;
import com.artique.api.feed.FeedException;
import com.artique.api.intertceptor.HttpPretty;
import com.artique.api.intertceptor.HttpRequestRepository;
import com.artique.api.member.exception.LoginException;
import com.artique.api.musical.MusicalException;
import com.artique.api.thumbs.ThumbsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {
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

  @ExceptionHandler(InterceptorException.class)
  public ResponseEntity<ErrorResponse> interceptor(InterceptorException e){
    return new ResponseEntity<>(ErrorResponse.builder().code(e.getInterceptorName()).message(e.getMessage()).build(),
            HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<ErrorResponse> invalidRequestBody(InvalidDataAccessApiUsageException e){
    String message = "invalid request body : "+e.getMessage();
    return new ResponseEntity<>(ErrorResponse.builder().code("REQUEST_BODY_MISMATCHING").message(message).build(),
            HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Object> missingQueryParam(MissingServletRequestParameterException e){
    String message = "missing query parameter ["+e.getParameterName()+"]";
    return new ResponseEntity<>(ErrorResponse.builder().code("REQUEST_PARAM_MISSING").message(message).build(),
            HttpStatus.BAD_REQUEST);
  }
  public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException e){
    String paramName = e.getName();
    String message="failed to convert, parameter name is ["+paramName+"]. given value : "+e.getValue();
    return new ResponseEntity<>(ErrorResponse.builder().code("PARAM_MISMATCHING").message(message).build(),
            HttpStatus.BAD_REQUEST);
  }
  public ResponseEntity<Object> cannotResolveRequestBody(HttpMessageNotReadableException e){
    String message = "invalid request body : "+e.getMessage();
    HttpPretty httpPretty;
    try {
      httpPretty=httpRequestRepository.getPrettyString();
    }catch (IOException exception){
      httpPretty=null;
    }
    return new ResponseEntity<>(ErrorResponse.builder().code("REQUEST_BODY_MISMATCHING").message(message)
            .httpRequest(httpPretty).build(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return missingQueryParam(ex);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    if(ex instanceof MethodArgumentTypeMismatchException){
      return handleTypeMismatch((MethodArgumentTypeMismatchException) ex);
    }
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return cannotResolveRequestBody(ex);
  }
}
