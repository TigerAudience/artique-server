package com.artique.api.profile.userReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileException extends RuntimeException{
  private String message;
  private String errorCode;
}
