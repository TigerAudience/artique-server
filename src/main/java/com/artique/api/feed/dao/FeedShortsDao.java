package com.artique.api.feed.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FeedShortsDao {
  //member info
  private String memberNickname;
  private String memberImageUrl;
  private String memberId;

  //musical info
  private String musicalName;
  private String posterUrl;
  //casting is review info
  private String casting;
  private String musicalId;

  //review info
  private LocalDate viewDate;
  private Double starRating;
  private Long thumbsCount;
  private String shortReview;
  private Long reviewId;
  private Boolean reviewSpoiler;

  //thumbsup info
  private Long thumbsId;

  public FeedShortsDao(String memNick,String memUrl,String memId,String musNm,String musUrl,String musCast,String musId,
                       LocalDate rDate,Double rRate,Long rThumbs,String rShort,Long rId,Boolean rSpoiler){
    this.memberNickname=memNick;
    this.memberImageUrl=memUrl;
    this.memberId=memId;

    this.musicalName=musNm;
    this.posterUrl=musUrl;
    this.casting=musCast;
    this.musicalId=musId;

    this.viewDate=rDate;
    this.starRating=rRate;
    this.thumbsCount=rThumbs;
    this.shortReview=rShort;
    this.reviewId=rId;
    this.reviewSpoiler=rSpoiler;

    this.thumbsId=null;
  }
  public void adjustThumbsId(Long thumbsId){
    this.thumbsId=thumbsId;
  }
}
