package com.artique.api.configurationPage;

import com.artique.api.configurationPage.response.NoticeInfo;
import com.artique.api.configurationPage.response.NoticeList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
  private final NoticeRepository noticeRepository;

  @GetMapping("/config/notice")
  public NoticeList getNotices(){
    return new NoticeList(noticeRepository.findAll().stream().map(NoticeInfo::of).toList());
  }
}
