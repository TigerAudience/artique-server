package com.artique.api.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchMusicalListDto {
  private List<SearchMusicalDto> musicals;
  private Integer size;

  public static SearchMusicalListDto of(List<SearchMusicalDto> dtos){
    return new SearchMusicalListDto(dtos,dtos.size());
  }
}
