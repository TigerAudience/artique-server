package com.artique.api.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportResponse {
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class ReportRes{
    private Boolean success;
  }
}
