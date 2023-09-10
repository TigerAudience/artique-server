package com.artique.api.search.dto;

import com.artique.api.entity.Musical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchMusicalDto {
  private String musicalId;
  private String posterUrl;
  private String title;

  public static SearchMusicalDto of(Musical m){
    return new SearchMusicalDto(m.getId(),m.getPosterUrl(),m.getName());
  }
}
