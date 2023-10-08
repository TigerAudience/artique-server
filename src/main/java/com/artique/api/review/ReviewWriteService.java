package com.artique.api.review;

import com.artique.api.entity.Member;
import com.artique.api.entity.Musical;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.musical.MusicalRepository;
import com.artique.api.review.request.ReviewUpdateRequest;
import com.artique.api.review.request.ReviewWriteRequest;
import com.artique.api.review.response.ReviewUpdateResult;
import com.artique.api.review.response.ReviewWriteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewWriteService {
  private final MusicalRepository musicalRepository;
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  @Transactional
  public ReviewWriteResult createReview(ReviewWriteRequest reviewForm,String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()-> new WriteReviewException("invalid member id","REVIEW-WRITE-001"));
    Musical musical = musicalRepository.findById(reviewForm.getMusicalId())
            .orElseThrow(()->new WriteReviewException("invalid musical id","REVIEW-WRITE-002"));

    Review review = reviewForm.toReview(member,musical);
    Review createdReview = reviewRepository.save(review);

    return ReviewWriteResult.of(createdReview);
  }
  @Transactional
  public ReviewUpdateResult updateReview(ReviewUpdateRequest reviewForm,String memberId){
    Review review = reviewRepository.findById(reviewForm.getReviewId())
            .orElseThrow(()->new WriteReviewException("invalid review id","REVIEW-UPDATE-001"));

    review.update(reviewForm,memberId);

    return ReviewUpdateResult.of(review);
  }
}
