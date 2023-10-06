package com.artique.api.profile.userReview.response;

import com.artique.api.profile.userReview.dto.UserCreateReview;
import com.artique.api.profile.userReview.dto.UserThumbsReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateReview {
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

  public static CreateReview of(UserCreateReview r){
    Long thumbsId = r.getThumbsId();
    return new CreateReview(r.getMemberNickname(),r.getMemberImageUrl(),r.getMemberId(),r.getMusicalName(),
            r.getPosterUrl(),r.getCasting(),r.getMusicalId(),r.getViewDate(),r.getStarRating(),r.getThumbsCount(),
            r.getShortReview(),r.getReviewId(),thumbsId!=null);
  }
}
