package com.artique.api.review.request;

import com.artique.api.entity.Member;
import com.artique.api.entity.Musical;
import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewWriteRequest {
  private Double starRating;
  private String shortReview;
  private String longReview;
  private List<String> casting;
  private LocalDate viewDate;
  private String seat;
  private String musicalId;

  public Review toReview(Member member, Musical musical){
    String reviewCasting="";
    for(int i=0;i<casting.size();i++){
      reviewCasting+=casting.get(i);
      if(i<casting.size()-1)
        reviewCasting+=",";
    }
    return new Review(null,starRating,shortReview,longReview,reviewCasting,viewDate,seat
            ,0L,member,musical,null, ZonedDateTime.now());
  }
}
