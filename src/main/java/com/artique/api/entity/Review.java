package com.artique.api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double starRating;
    @Column(columnDefinition = "TEXT")
    private String shortReview;
    @Column(columnDefinition = "TEXT")
    private String longReview;
    private String casting;
    private LocalDate viewDate;
    private String seat;
    private Long thumbsUp;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Musical musical;
}