package com.artique.api.entity;

import com.artique.api.feed.Thumbs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private ZonedDateTime createdAt;

    public void thumbsUp(){
        this.thumbsUp+=1;
    }
    public void thumbsCancel(){
        this.thumbsUp-=1;
    }
}