package com.artique.api.profile.summary;

import com.artique.api.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
@Getter
public class ReviewAnalyzer {
  public ReviewAnalyzer(){
    this.statistics = initStatistics();
    totalReviewCount=0L;
    maxStarRate=0D;
    maxStarRateCount=0L;
    averageRate=0D;
  }
  private Map<Double,Long> statistics;
  private Long totalReviewCount;
  private Double maxStarRate;
  private Long maxStarRateCount;
  private Double averageRate;
  public void analysisAllReview(){
    double starRateSum=0D;
    for(Map.Entry<Double,Long> entry : statistics.entrySet()){
      Double rate = entry.getKey();
      Long rateCount = entry.getValue();
      starRateSum+=(rate*rateCount);
      if (maxStarRateCount<=rateCount){
        maxStarRateCount=rateCount;
        maxStarRate=rate;
      }
    }
    averageRate = totalReviewCount>0? (starRateSum/totalReviewCount) : 0D;
  }
  public void analysisReview(Review review){
    putIntoStatistics(review);
    this.totalReviewCount+=1;
  }
  private void putIntoStatistics(Review review){
    Double key = getKeyFromStarRating(review.getStarRating());
    Long cnt = statistics.get(key);
    statistics.put(key,cnt+1);
  }
  private Double getKeyFromStarRating(Double starRating){
    for(int i=0;i<10;i++){
      if(starRating>=(5.0-i*0.5))
        return 5.0-i*0.5;
    }
    return 0.5D;
  }


  private Map<Double,Long> initStatistics(){
    Map<Double,Long> statistics = new HashMap<>();
    for(int i=1;i<=10;i++){
      statistics.put(0.5D*i,0L);
    }
    return statistics;
  }
}
