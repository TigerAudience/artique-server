package com.artique.api.profile.summary.response;

import com.artique.api.profile.summary.ReviewAnalyzer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberReviewRateStatistics {
  private TreeMap<Double,Long> statistic;
  private Double averageRate;
  private Long totalReviewCount;
  private Double maxStarRate;

  public static MemberReviewRateStatistics of(ReviewAnalyzer analyzer){
    TreeMap<Double,Long> sortedStatistics = new TreeMap<>(analyzer.getStatistics());
    return new MemberReviewRateStatistics(sortedStatistics, analyzer.getAverageRate(),
            analyzer.getTotalReviewCount(), analyzer.getMaxStarRate());
  }
}
