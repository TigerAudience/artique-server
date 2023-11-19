package com.artique.api.exception.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RepositoryException extends RuntimeException{
  private String message;
  private String code;
}
