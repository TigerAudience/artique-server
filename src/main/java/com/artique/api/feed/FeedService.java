package com.artique.api.feed;

import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.feed.response.FeedShorts;
import com.artique.api.feed.response.FeedSliceDto;
import com.artique.api.profile.userReview.dto.ReviewThumb;
import com.artique.api.profile.userReview.dto.UserCreateReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final ReviewRepository reviewRepository;

  public FeedSliceDto mainFeedsWithMember(String memberId,int page,int size, String type){

    //4가지 버전의 피드 서비스 제공
    // 1.최신순(type:recent) 2.공감 많은 순(type:many-thumbs)
    // 3.긴줄평 있는 리뷰(type:long) 4.별점5.0 리뷰(type:five-star-rating)
    Slice<FeedShortsDao> feeds = findFeedShortsDao(page,size,type);

    List<FeedShortsDao> feedList = feeds.stream().toList();
    boolean hasNext = feeds.hasNext();
    int pageAblePage = feeds.getPageable().getPageNumber();
    int pageAbleSize = feeds.getPageable().getPageSize();

    mapThumbsId(feedList,memberId);

    List<FeedShorts> feedShorts = feedList.stream().map(FeedShorts::of).toList();

    return new FeedSliceDto(feedShorts,hasNext,pageAblePage,pageAbleSize);
  }
  private Slice<FeedShortsDao> findFeedShortsDao(int page,int size,String type){
    return switch (type) {
      case "recent" -> reviewRepository.findPageReviewsByMemberSliceOrderByCreatedAt(PageRequest.of(page, size));
      case "many-thumbs" -> reviewRepository.findPageReviewsByMemberSliceOrderByThumbsUp(PageRequest.of(page, size));
      case "long" -> reviewRepository.findLongPageReviewsByMemberSlice(PageRequest.of(page, size));
      case "five-star-rating" ->
              reviewRepository.findStarRatingFivePageReviewsByMemberSlice(PageRequest.of(page, size));
      default -> reviewRepository.findPageReviewsByMemberSliceOrderByCreatedAt(PageRequest.of(page, size));
    };
  }

  public void mapThumbsId(List<FeedShortsDao> feeds,String memberId){
    List<Long> reviewIds = feeds.stream().map(FeedShortsDao::getReviewId).toList();
    List<ReviewThumb> thumbs =  reviewRepository.findThumbsByReviewIds(reviewIds,memberId);

    HashMap<Long,Long> thumbsMap = toMap(thumbs);

    for(FeedShortsDao feed: feeds){
      feed.adjustThumbsId(thumbsMap.get(feed.getReviewId()));
    }
  }
  private HashMap<Long, Long> toMap(List<ReviewThumb> reviewThumbs){
    HashMap<Long, Long> reviewThumbMap = new HashMap<>();
    for(ReviewThumb rt : reviewThumbs)
      reviewThumbMap.put(rt.getReviewId(),rt.getThumbsId());
    return reviewThumbMap;
  }
}
