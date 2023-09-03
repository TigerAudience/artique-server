package com.artique.api.feed;

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

    Slice<FeedDto> feeds = reviewRepository.findPageReviewsByMemberSlice(pageRequest,memberId);

    List<FeedDto> feedList = feeds.stream().toList();
    boolean hasNext = feeds.hasNext();
    int pageAblePage = feeds.getPageable().getPageNumber();
    int pageAbleSize = feeds.getPageable().getPageSize();

    return new FeedSliceDto(feedList,hasNext,pageAblePage,pageAbleSize);
  }
}
