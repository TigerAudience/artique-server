package com.artique.api.profile.summary;

import com.artique.api.entity.Member;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.member.MemberRepository;
import com.artique.api.musical.dto.MusicalRateStatistics;
import com.artique.api.profile.summary.response.MemberReviewRateStatistics;
import com.artique.api.profile.summary.response.MemberSummary;
import com.artique.api.profile.userReview.ProfileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

  private final ReviewRepository reviewRepository;

  private final MemberRepository memberRepository;
  public MemberSummary getSummary(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->invalidMemberId(memberId));

    return MemberSummary.of(member);
  }
  public MemberReviewRateStatistics analysis(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->invalidMemberId(memberId));

    List<Review> reviews = reviewRepository.findReviewsByMemberId(memberId);

    ReviewAnalyzer reviewAnalyzer = new ReviewAnalyzer();
    for(Review r: reviews){
      reviewAnalyzer.analysisReview(r);
    }
    reviewAnalyzer.analysisAllReview();

    return MemberReviewRateStatistics.of(reviewAnalyzer);
  }
  private ProfileException invalidMemberId(String requestId){
    requestId = requestId == null ? "null id" : requestId;
    return new ProfileException("invalid member id ["+requestId+"]","PROFILE-001");
  }

}
