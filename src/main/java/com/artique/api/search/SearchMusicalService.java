package com.artique.api.search;

import com.artique.api.entity.Musical;
import com.artique.api.musical.MusicalRepository;
import com.artique.api.search.dto.SearchMusicalDto;
import com.artique.api.search.dto.SearchMusicalListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMusicalService {

  private final MusicalRepository musicalRepository;

  public SearchMusicalListDto searchMusical(String keyWord,MusicalOrderBy musicalOrderBy){
    List<Musical> musicals;
    if(musicalOrderBy.equals(MusicalOrderBy.REVIEW))
      musicals = musicalRepository.findMusicalsByKeyWordOrderByReviews(keyWord);
    else
      musicals = musicalRepository.findMusicalsByKeyWordOrderByTime(keyWord);
    List<SearchMusicalDto> musicalDtos = musicals.stream().map(SearchMusicalDto::of).toList();
    return SearchMusicalListDto.of(musicalDtos);
  }
}
