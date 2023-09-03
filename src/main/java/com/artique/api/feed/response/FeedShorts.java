package com.artique.api.feed.response;

import com.artique.api.feed.dao.FeedShortsDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FeedShorts {
  //member info
  private String memberNickname;
  private String memberImageUrl;
  private String memberId;

  //musical info
  private String musicalName;
  private String posterUrl;
  private String casting;
  private String musicalId;

  //review info
  private LocalDate viewDate;
  private Double starRating;
  private Long thumbsCount;
  private String shortReview;
  private Long reviewId;

  //thumbsup info
  private Boolean isThumbsUp;

  public static FeedShorts of(FeedShortsDao feedShorts){
    return new FeedShorts(feedShorts.getMemberNickname(),feedShorts.getMemberImageUrl(),
            feedShorts.getMemberId(), feedShorts.getMusicalName(),feedShorts.getPosterUrl(),
            feedShorts.getCasting(),feedShorts.getMusicalId(),feedShorts.getViewDate(),feedShorts.getStarRating(),
            feedShorts.getThumbsCount(),feedShorts.getShortReview(),feedShorts.getReviewId(),
            feedShorts.getThumbsId() != null);
  }
}
