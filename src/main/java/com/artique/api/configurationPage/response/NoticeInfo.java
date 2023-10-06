package com.artique.api.configurationPage.response;

import com.artique.api.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeInfo {
  private String title;
  private String content;
  public static NoticeInfo of(Notice notice){
    return new NoticeInfo(notice.getTitle(),notice.getContent());
  }
}
