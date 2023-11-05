package com.artique.api.profile.userReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserThumbsReview {

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

  public UserThumbsReview(String memName,String memUrl, String memId,String musName,String musUrl,String rCast,
                          String musId, LocalDate rView,Double rStar,Long rThumb,String rShort,Boolean reviewSpoiler,Long rId){
    this.memberNickname=memName;
    this.memberImageUrl=memUrl;
    this.memberId=memId;
    this.musicalName=musName;
    this.posterUrl=musUrl;
    this.casting=rCast;
    this.musicalId=musId;
    this.viewDate=rView;
    this.starRating=rStar;
    this.thumbsCount=rThumb;
    this.shortReview=rShort;
    this.reviewId=rId;
    this.reviewSpoiler=reviewSpoiler;
    this.thumbsId=null;
  }

  public void adjustThumbsId(Long thumbsId){
    this.thumbsId=thumbsId;
  }
}
