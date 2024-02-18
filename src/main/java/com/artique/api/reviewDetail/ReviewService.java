package com.artique.api.reviewDetail;

import com.artique.api.entity.Review;
import com.artique.api.feed.FeedService;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.reviewDetail.dto.ReviewDetailDto;
import com.artique.api.reviewDetail.dto.ReviewDetailThumbsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final FeedService feedService;
  public ReviewDetailDto getDetail(Long reviewId, String memberId){
    Review review = reviewRepository.findReviewByIdJoinFetchMemberMusical(reviewId)
            .orElseThrow(()->new ReviewException("REVIEW-001","invalid review id"));

    Optional<ReviewDetailThumbsInfo> thumbsInfo = reviewRepository.findThumbsByMemberId(memberId).stream().findFirst();
    return ReviewDetailDto.of(review,thumbsInfo);
  }
}
