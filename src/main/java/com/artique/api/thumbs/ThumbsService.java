package com.artique.api.thumbs;

import com.artique.api.entity.Member;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.feed.Thumbs;
import com.artique.api.feed.ThumbsRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.thumbs.dto.ThumbsResponse;
import com.artique.api.thumbs.dto.ThumbsReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThumbsService {
  private final ThumbsRepository thumbsRepository;
  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;
  @Transactional
  public ThumbsResponse process(String memberId, ThumbsReq req){
    Review review = reviewRepository.findById(req.getReviewId())
            .orElseThrow(()->new ThumbsException("invalid review Id","THUMBS-001"));
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new ThumbsException("invalid member Id, error from session","THUMBS-002"));

    Optional<Thumbs> thumbs = thumbsRepository.findThumbsByMemberAndReview(member.getId(),review.getId());

    if(thumbs.isEmpty() && req.getThumbsUp())
      return thumbsUp(member,review);
    else if(thumbs.isPresent() && !req.getThumbsUp())
      return thumbsCancel(thumbs.get(),member,review);

    throw new ThumbsException("invalid isThumbsUp","THUMBS-003");
  }

  private ThumbsResponse thumbsUp(Member member,Review review){
    Thumbs thumbs = thumbsRepository.save(new Thumbs(null,member,review, LocalDateTime.now()));
    review.thumbsUp();
    return ThumbsResponse.of(thumbs);
  }
  private ThumbsResponse thumbsCancel(Thumbs thumbs,Member member,Review review){
    thumbsRepository.delete(thumbs);
    review.thumbsCancel();
    return ThumbsResponse.of(member,review);
  }
}
