package com.artique.api.profile.edit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImageUploadResponse {
  private String url;
  public static ImageUploadResponse of(String url){
    return new ImageUploadResponse(url);
  }
}
