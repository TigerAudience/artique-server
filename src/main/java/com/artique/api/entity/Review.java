package com.artique.api.entity;

import com.artique.api.feed.Thumbs;
import com.artique.api.review.WriteReviewException;
import com.artique.api.review.request.ReviewUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

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
    private boolean shortSpoiler;
    private boolean longSpoiler;

    public void thumbsUp(){
        this.thumbsUp+=1;
    }
    public void thumbsCancel(){
        this.thumbsUp-=1;
    }

    public void update(ReviewUpdateRequest reviewForm,String memberId){
        checkAuthority(memberId);
        this.starRating=reviewForm.getStarRating();
        this.shortReview=reviewForm.getShortReview();
        this.longReview=reviewForm.getLongReview();
        List<String> casting=reviewForm.getCasting();
        String reviewCasting="";
        for(int i=0;i<casting.size();i++){
            reviewCasting+=casting.get(i);
            if(i<casting.size()-1)
                reviewCasting+=",";
        }
        this.casting=reviewCasting;
        this.viewDate=reviewForm.getViewDate();
        this.seat=reviewForm.getSeat();
    }
    public void checkAuthority(String memberId){
        if(!Objects.equals(member.getId(), memberId))
            throw new WriteReviewException("Author and user do not match.","REVIEW-UPDATE-002");
    }
}