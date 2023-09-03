package com.artique.api.musical;

import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MusicalController {
  private final MusicalService musicalService;

  @GetMapping("/musical/detail")
  public MusicalInfo detail(@RequestParam(value = "musical-id")String musicalId){
    return musicalService.getDetail(musicalId);
  }

  @GetMapping("/musical/reviews")
  public MusicalReviewSmallList reviews(@RequestParam(value = "musical-id")String musicalId){
    return
  }
}
