package com.artique.api.musical.dao;

import com.artique.api.entity.Musical;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MusicalWithRating {
  private String musicalId;
  private String posterUrl;
  private String title;
  private Double averageScore;
  private Long reviewCount;
  private LocalDate beginDate;
  private LocalDate endDate;
  private String place;
  private String duration;
  private String casting;
  private String plot;

  public static MusicalWithRating of(Musical m){
    return new MusicalWithRating(m.getId(),m.getPosterUrl(),m.getName(),0D,0L,
            m.getBeginDate(),m.getEndDate(),m.getPlaceName(),m.getRunningTime(),m.getCasting(),m.getPlot());
  }
}
