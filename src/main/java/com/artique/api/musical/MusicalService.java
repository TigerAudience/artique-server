package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalReview;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicalService {
  private final MusicalRepository musicalRepository;
  private final ReviewRepository reviewRepository;

  public MusicalInfo getDetail(String musicalId){
    MusicalWithRating musical = musicalRepository.findMusicalWithRating(musicalId)
            .orElseThrow(()->new MusicalException("invalid musical id","MUSICAL-001"));

    return MusicalInfo.of(musical);
  }

  public MusicalReviewSmallList getReviews(String memberId, String musicalId){
    PageRequest pageRequest = PageRequest.of(0,3);
    Page<MusicalReview> reviews = reviewRepository.findPageMusicalReviewsByMusicalId(pageRequest,musicalId,memberId);
    return MusicalReviewSmallList.of(reviews);
  }
}
