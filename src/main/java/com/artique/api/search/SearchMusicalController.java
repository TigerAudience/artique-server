package com.artique.api.search;

import com.artique.api.musical.ReviewOrderBy;
import com.artique.api.search.dto.SearchMusicalListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchMusicalController {
  private final SearchMusicalService searchMusicalService;

  @GetMapping("/search")
  public SearchMusicalListDto search(@RequestParam(value = "key-word")String keyWord
          ,@RequestParam(value = "order-by") MusicalOrderBy musicalOrderBy){
    return searchMusicalService.searchMusical(keyWord,musicalOrderBy);
  }
}
