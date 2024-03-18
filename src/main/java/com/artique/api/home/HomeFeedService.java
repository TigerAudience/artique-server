package com.artique.api.home;

import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.home.Response.BannerList;
import com.artique.api.home.Response.HomeReviewList;
import com.artique.api.home.Response.RecommendMusicalList;
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
  private final ArtiqueRecommendMusicalRepository artiqueRecommendMusicalRepository;
  private final HomeBannerRepository homeBannerRepository;
  public HomeReviewList findRecentReviews(){
    List<Review> reviews = reviewRepository.findReviewsOrderByCreatedAt(PageRequest.of(0,5));
    return HomeReviewList.of("최신 리뷰",reviews);
  }
  public HomeReviewList findManyThumbsUpReviews(){
    List<Review> reviews = reviewRepository.findReviewsOrderByThumbsUp(PageRequest.of(0,5));
    return HomeReviewList.of("공감 많은 리뷰",reviews);
  }
  public HomeReviewList findIncludingLongReviews(){
    List<Review> reviews = reviewRepository.findLongReviews(PageRequest.of(0,5));
    return HomeReviewList.of("긴줄 평 있는 리뷰",reviews);
  }
  public HomeReviewList findFiveStarRatingReviews(){
    List<Review> reviews = reviewRepository.findStarRatingFiveReviews(PageRequest.of(0,5));
    return HomeReviewList.of("별점 5점 리뷰",reviews);
  }
  public RecommendMusicalList getRecommendMusicalList(){
    return RecommendMusicalList.of(artiqueRecommendMusicalRepository.findAll(PageRequest.of(0,5)).toList());
  }
  public BannerList getBannerList(){
    return BannerList.of(homeBannerRepository.getHomeBannerOrderBySequence());
  }
}
