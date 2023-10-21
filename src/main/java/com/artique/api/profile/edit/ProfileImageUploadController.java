package com.artique.api.profile.edit;

import com.artique.api.profile.edit.response.ImageUploadResponse;
import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileImageUploadController {
  private final ImageUploadService imageUploadService;

  @PostMapping("/image")
  public ImageUploadResponse upload(@LoginUser String memberId, @RequestPart(value = "file") MultipartFile file){
    return ImageUploadResponse.of(imageUploadService.changeImage(file, memberId));
  }
}
