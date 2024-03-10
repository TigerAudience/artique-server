package com.artique.api.home;

import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.home.Response.RecentReviewList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeFeedService {
  private final ReviewRepository reviewRepository;
  public RecentReviewList findRecentReviews(){
    List<Review> reviews = reviewRepository.findReviewsOrderByCreatedAt(PageRequest.of(0,5));
    return RecentReviewList.of(reviews);
  }
}
