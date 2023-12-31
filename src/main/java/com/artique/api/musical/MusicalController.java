package com.artique.api.musical;

import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalRateStatistics;
import com.artique.api.musical.dto.MusicalReviewSlice;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import com.artique.api.resolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MusicalController implements MusicalControllerSwagger{
  private final MusicalService musicalService;

  @GetMapping("/musical/detail")
  public MusicalInfo detail(@RequestParam(value = "musical-id")String musicalId){
    return musicalService.getDetail(musicalId);
  }

  @GetMapping("/musical/reviews")
  public MusicalReviewSmallList reviews(@LoginUser String memberId,@RequestParam(value = "musical-id")String musicalId){
    return musicalService.getReviews(memberId,musicalId);
  }

  @GetMapping("/musical/rate/statistics")
  public MusicalRateStatistics rateStatistics(@RequestParam(value = "musical-id")String musicalId){
    return musicalService.analysis(musicalId);
  }

  @GetMapping("/musical/reviews/all")
  public MusicalReviewSlice allReviews(@LoginUser String memberId, @RequestParam("musical-id")String musicalId,
                                       @RequestParam("page") int page,@RequestParam("size") int size,
                                       @RequestParam(value = "order-by") ReviewOrderBy reviewOrderBy){
    return musicalService.getReviews(memberId,musicalId,page,size, reviewOrderBy);
  }
}
