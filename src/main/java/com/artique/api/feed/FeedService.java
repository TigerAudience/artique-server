package com.artique.api.feed;

import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.feed.response.FeedShorts;
import com.artique.api.feed.response.FeedSliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final ReviewRepository reviewRepository;

  public FeedSliceDto mainFeeds(){
    return null;
  }
  public FeedSliceDto mainFeedsWithMember(String memberId,int page,int size){
    PageRequest pageRequest = PageRequest.of(page,size);

    Slice<FeedShortsDao> feeds = reviewRepository.findPageReviewsByMemberSlice(pageRequest,memberId);

    List<FeedShortsDao> feedList = feeds.stream().toList();
    boolean hasNext = feeds.hasNext();
    int pageAblePage = feeds.getPageable().getPageNumber();
    int pageAbleSize = feeds.getPageable().getPageSize();

    List<FeedShorts> feedShorts = feedList.stream().map(FeedShorts::of).toList();

    return new FeedSliceDto(feedShorts,hasNext,pageAblePage,pageAbleSize);
  }
}
