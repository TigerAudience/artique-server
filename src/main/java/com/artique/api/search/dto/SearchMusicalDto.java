package com.artique.api.search.dto;

import com.artique.api.entity.Musical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.StringBuilders;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchMusicalDto {
  private String musicalId;
  private String posterUrl;
  private String title;
  private String duration;

  public static SearchMusicalDto of(Musical m){
    DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String beginDate = m.getBeginDate().format(dateformat);
    String endDate = m.getEndDate().format(dateformat);
    String duration = beginDate + " ~ " + endDate;
    return new SearchMusicalDto(m.getId(),m.getPosterUrl(),m.getName(), duration);
  }
}
