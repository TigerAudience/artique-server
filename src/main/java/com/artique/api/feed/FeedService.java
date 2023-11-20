package com.artique.api.feed;

import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.feed.response.FeedShorts;
import com.artique.api.feed.response.FeedSliceDto;
import com.artique.api.profile.userReview.dto.ReviewThumb;
import com.artique.api.profile.userReview.dto.UserCreateReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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

  public FeedSliceDto mainFeedsWithMember(String memberId,int page,int size){
    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"thumbsUp"));

    Slice<FeedShortsDao> feeds = reviewRepository
            .findPageReviewsByMemberSlice(pageRequest, ZonedDateTime.now().minusDays(7),ZonedDateTime.now());

    List<FeedShortsDao> feedList = feeds.stream().toList();
    boolean hasNext = feeds.hasNext();
    int pageAblePage = feeds.getPageable().getPageNumber();
    int pageAbleSize = feeds.getPageable().getPageSize();

    mapThumbsId(feedList,memberId);

    List<FeedShorts> feedShorts = feedList.stream().map(FeedShorts::of).toList();

    return new FeedSliceDto(feedShorts,hasNext,pageAblePage,pageAbleSize);
  }

  private void mapThumbsId(List<FeedShortsDao> feeds,String memberId){
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
