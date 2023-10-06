package com.artique.api.profile.summary.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberReviewRateStatistics {
  private TreeMap<Double,Long> statistic;
}
