package com.artique.api.musical.dto;

import com.artique.api.musical.dao.MusicalWithRating;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MusicalInfo {
  private String musicalId;
  private String posterUrl;
  private String title;
  private String averageScore;
  private String date;
  private String place;
  private String duration;
  private String casting;
  private String story;

  private static String averageScoreBuilder(double averageScore, long count){
    return String.format("%.1f",averageScore)+" ("+ count +")";
  }
  private static String dateBuilder(LocalDate beginDate,LocalDate endDate){
    String endDateString="";
    if (endDate == null)
      endDateString="오픈런";
    else
      endDateString=endDate.toString();
    return beginDate.toString()+" ~ "+endDateString;
  }

  public static MusicalInfo of(MusicalWithRating m){
    return new MusicalInfo(m.getMusicalId(),m.getPosterUrl(),
            m.getTitle(),averageScoreBuilder(m.getAverageScore(),m.getReviewCount()),
            dateBuilder(m.getBeginDate(),m.getEndDate()),m.getPlace(),m.getDuration(),m.getCasting(),m.getPlot());
  }
}
