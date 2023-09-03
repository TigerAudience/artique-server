package com.artique.api.musical.dto;

import com.artique.api.musical.dao.MusicalReviewDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
public class MusicalReviewSmallList {
  private Long totalReviewCount;
  private List<MusicalReview> reviews;

  public static MusicalReviewSmallList of(Page<MusicalReviewDao> daos){
    List<MusicalReview> musicalReviews = daos.stream().map(MusicalReview::of).toList();
    Long totalCount = daos.getTotalElements();
    return new MusicalReviewSmallList(totalCount,musicalReviews);
  }
}
