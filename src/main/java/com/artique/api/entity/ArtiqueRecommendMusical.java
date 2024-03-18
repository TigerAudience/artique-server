package com.artique.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ArtiqueRecommendMusical {
  @Id
  private Long id;
  @ManyToOne
  private Musical musical;
}
