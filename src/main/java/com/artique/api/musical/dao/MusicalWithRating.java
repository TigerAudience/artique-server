package com.artique.api.musical.dao;

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
}
