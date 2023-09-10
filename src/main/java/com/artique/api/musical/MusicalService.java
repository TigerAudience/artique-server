package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.*;
import com.artique.api.musical.dao.MusicalReviewDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    Musical musicalEntity = musicalRepository.findById(musicalId)
            .orElseThrow(()->new MusicalException("invalid musical id","MUSICAL-001"));
    MusicalWithRating musical = musicalRepository.findMusicalWithRating(musicalId);
    if (musical!=null)
      return MusicalInfo.of(musical);
    return MusicalInfo.of(MusicalWithRating.of(musicalEntity));
  }

  public MusicalReviewSmallList getReviews(String memberId, String musicalId){
    PageRequest pageRequest = PageRequest.of(0,3,Sort.by(Sort.Direction.DESC,"thumbsUp"));
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


  public MusicalReviewSlice getReviews(String memberId,String musicalId, int page, int size, OrderBy orderBy){
    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, orderBy.getFieldName()));

    Slice<MusicalReviewDao> reviewDaos = reviewRepository.findMusicalReviewsByMusicalId(pageRequest,musicalId,memberId);

    List<MusicalReview> reviews = reviewDaos.stream().map(MusicalReview::of).toList();
    int pageNumber = reviewDaos.getPageable().getPageNumber();
    int pageSize = reviewDaos.getPageable().getPageSize();
    boolean hasNext = reviewDaos.hasNext();

    return new MusicalReviewSlice(reviews,hasNext,pageNumber,pageSize);
  }
}
