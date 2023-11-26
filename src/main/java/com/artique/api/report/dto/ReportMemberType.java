package com.artique.api.report.dto;

import lombok.Getter;

@Getter
public enum ReportMemberType {
  INAPPROPRIATE("부적절한 프로필"),HATE("혐오 발언 및 상징"),PRETEND("타인 사칭"),ETC("기타");
  private final String detail;
  ReportMemberType(String detail){
    this.detail=detail;
  }
  public static String getDetail(String str){
    for(ReportMemberType r : ReportMemberType.values()){
      if(r.toString().equals(str))
        return r.getDetail();
    }
    return "doesn't match any detail ["+str+"]";
  }
}
