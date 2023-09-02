package com.artique.api.feed;

import com.artique.api.entity.Member;
import com.artique.api.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
//@Table(uniqueConstraints = @UniqueConstraint(name = "UniqueMemberAndReview",columnNames = {"member","address"}))
public class Thumbs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Member member;
  @ManyToOne
  private Review review;
}
