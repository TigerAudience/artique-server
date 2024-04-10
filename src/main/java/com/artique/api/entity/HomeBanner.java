package com.artique.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class HomeBanner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String imageUrl;
  private String href;
  private Integer sequence;
  // 0:외부 링크, 1:뮤지컬 아이디, 2:리뷰 아이디
  private Integer type;
}
