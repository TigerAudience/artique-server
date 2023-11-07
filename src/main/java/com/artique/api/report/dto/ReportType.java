package com.artique.api.report.dto;

import com.artique.api.musical.MusicalException;
import com.artique.api.profile.userReview.UserReviewOrderBy;
import lombok.Getter;

@Getter
public enum ReportType {

  SPOILER("spoiler"),CREATE("inappropriate-review");

  private final String type;
  ReportType(String type){
    this.type=type;
  }

  public static ReportType of(String str){
    if(str==null)
      throw new ReportException("report type can't be null","REPORT-001");
    for(ReportType r : ReportType.values()){
      if(r.getType().equals(str))
        return r;
    }
    throw new ReportException("invalid report type name","REPORT-002");
  }
}
