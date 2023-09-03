package com.artique.api.entity;

import com.artique.api.feed.Thumbs;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
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
    @OneToMany(mappedBy = "review",fetch = FetchType.LAZY)
    private List<Thumbs> thumbs;
}