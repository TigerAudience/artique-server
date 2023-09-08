package com.artique.api.thumbs.dto;

import com.artique.api.entity.Member;
import com.artique.api.entity.Review;
import com.artique.api.feed.Thumbs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThumbsResponse {
  private String memberId;
  private Long reviewId;
  private String result;

  public static ThumbsResponse of(Thumbs t){
    return new ThumbsResponse(t.getMember().getId(),t.getReview().getId(),"up");
  }
  public static ThumbsResponse of(Member member, Review review){
    return new ThumbsResponse(member.getId(),review.getId(),"cancel");
  }
}
