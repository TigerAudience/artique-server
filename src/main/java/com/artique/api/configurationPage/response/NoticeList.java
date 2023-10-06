package com.artique.api.configurationPage.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeList {
  private List<NoticeInfo> notices;
}
