package com.artique.api.musical.dto;

import com.artique.api.musical.dao.MusicalReviewDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@AllArgsConstructor
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
  private LocalDate writtenDate;
  private Boolean reviewSpoiler;

  //thumbsup info
  private Boolean isThumbsUp;

  public static MusicalReview of(MusicalReviewDao dao){
    return new MusicalReview(dao.getMemberNickname(),dao.getMemberImageUrl(),dao.getMemberId(),dao.getViewDate(),
            dao.getStarRating(),dao.getThumbsCount(),dao.getShortReview(),dao.getReviewId(),
            dao.getCreatedAt().toLocalDate(),dao.getReviewSpoiler(),dao.getThumbsId() != null);
  }
}
