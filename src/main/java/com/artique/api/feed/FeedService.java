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

    //최근 일주일동안 작성된 리뷰중, 공감 수 많은 순서로 리뷰 가져오기
    //회의 결과, 리뷰가 지속적으로 쌓일 정도로 서비스가 조금 더 성장했을 때 사용할 전략으로 결정
    /*
    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"thumbsUp"));
    Slice<FeedShortsDao> feeds = reviewRepository
            .findPageReviewsByMemberSlice(pageRequest, ZonedDateTime.now().minusDays(7),ZonedDateTime.now());
     */
    //작성된 모든 리뷰 중, 공감 수 많은 순서로 리뷰 가져오기
    //2024/01/04 회의 결과, 피드의 리뷰 회전율이 너무 낮아 해당 방식은 보류하기로 결정
    /*
    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"thumbsUp"));
    Slice<FeedShortsDao> feeds = reviewRepository
            .findPageReviewsByMemberSliceAllDate(pageRequest);
     */
    //작성된 모든 리뷰 중, 가장 최근에 작성된 순서로 리뷰 가져오기
    PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt"));
    Slice<FeedShortsDao> feeds = reviewRepository
            .findPageReviewsByMemberSliceAllDate(pageRequest);

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
