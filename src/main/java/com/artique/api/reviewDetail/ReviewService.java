package com.artique.api.reviewDetail;

import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.reviewDetail.dto.ReviewDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  public ReviewDetailDto getDetail(Long reviewId){
    Review review = reviewRepository.findReviewByIdJoinFetchMemberMusical(reviewId)
            .orElseThrow(()->new ReviewException("REVIEW-001","invalid review id"));
    return ReviewDetailDto.of(review);
  }
}
