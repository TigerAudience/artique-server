package com.artique.api.musical.dto;

import com.artique.api.musical.dao.MusicalReviewDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MusicalReview {

  //member info
  private String memberNickname;
  private String memberImageUrl;
  private String memberId;

  //review info
  private LocalDate viewDate;
  private Double starRating;
  private Long thumbsCount;
  private String shortReview;
  private Long reviewId;

  //thumbsup info
  private Boolean isThumbsUp;

  public static MusicalReview of(MusicalReviewDao dao){
    return new MusicalReview(dao.getMemberNickname(),dao.getMemberImageUrl(),dao.getMemberId(),dao.getViewDate(),
            dao.getStarRating(),dao.getThumbsCount(),dao.getShortReview(),dao.getReviewId(),
            dao.getThumbsId() != null);
  }
}
