package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicalService {
  private final MusicalRepository musicalRepository;

  public MusicalInfo getDetail(String musicalId){
    MusicalWithRating musical = musicalRepository.findMusicalWithRating(musicalId)
            .orElseThrow(()->new MusicalException("invalid musical id","MUSICAL-001"));

    return MusicalInfo.of(musical);
  }

  public MusicalReviewSmallList getReviews(String musicalId){

  }
}
