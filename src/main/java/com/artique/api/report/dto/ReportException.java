package com.artique.api.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReportException extends RuntimeException{
  private String message;
  private String errorCode;
}
