package com.artique.api.feed;

import com.artique.api.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final ReviewRepository reviewRepository;

  public FeedListDto mainFeeds(){
    PageRequest pageRequest = PageRequest.of(0,10);
    Slice<Review> result = reviewRepository.findPageReviewBySlice(pageRequest);
    List<Review> reviewList = result.getContent();
    List<FeedDto> feeds = new ArrayList<>();
    for(Review r : reviewList){
      System.out.println(r.getId());
      feeds.add(new FeedDto(r.getMember().getNickname(),r.getShortReview(),r.getThumbs().size(),r.getMusical().getName()));
    }
    return new FeedListDto(feeds);
  }
  public FeedListDto mainFeedsWithMember(){
    PageRequest pageRequest = PageRequest.of(0,10);
    Slice<Review> result = reviewRepository.findPageReviewBySliceWithUser(pageRequest,"abcdef");
    List<Review> reviewList = result.getContent();
    List<FeedDto> feeds = new ArrayList<>();
    for(Review r : reviewList){
      System.out.println(r.getId());
      feeds.add(new FeedDto(r.getMember().getNickname(),r.getShortReview(),r.getThumbs().size(),r.getMusical().getName()));
    }
    return new FeedListDto(feeds);
  }
}
