package com.artique.api.musical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.TreeMap;

@AllArgsConstructor
@Getter
public class MusicalRateStatistics {
  private TreeMap<Double,Long> statistic;
}
