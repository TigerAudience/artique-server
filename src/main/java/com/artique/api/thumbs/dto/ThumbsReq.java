package com.artique.api.thumbs.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ThumbsReq {
  @NotNull
  private Long reviewId;
  @NotNull
  private Boolean thumbsUp;
}
