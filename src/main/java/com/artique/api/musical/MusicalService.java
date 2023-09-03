package com.artique.api.musical;

import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.MusicalInfo;
import com.artique.api.musical.dto.MusicalRateStatistics;
import com.artique.api.musical.dao.MusicalReviewDao;
import com.artique.api.musical.dto.MusicalReviewSmallList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    Page<MusicalReviewDao> reviews = reviewRepository.findPageMusicalReviewsByMusicalId(pageRequest,musicalId,memberId);
    return MusicalReviewSmallList.of(reviews);
  }

  public MusicalRateStatistics analysis(String musicalId){
    List<Review> reviews = reviewRepository.findReviewsByMusicalId(musicalId);
    Map<Double,Long> statistics =createStatistics();
    for(Review r : reviews){
      Double key = getKeyFromStarRating(r.getStarRating());
      Long cnt = statistics.get(key);
      statistics.put(key,cnt+1);
    }
    TreeMap<Double,Long> sortedStatistics = new TreeMap<>(statistics);
    return new MusicalRateStatistics(sortedStatistics);
  }
  private Double getKeyFromStarRating(Double starRating){
    for(int i=0;i<10;i++){
      if(starRating>=(5.0-i*0.5))
        return 5.0-i*0.5;
    }
    return 0.5D;
  }
  private Map<Double,Long> createStatistics(){
    Map<Double,Long> statistics = new HashMap<>();
    for(int i=1;i<=10;i++){
      statistics.put(0.5D*i,0L);
    }
    return statistics;
  }
}
