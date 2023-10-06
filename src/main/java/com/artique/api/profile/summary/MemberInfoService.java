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
            .orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    return MemberSummary.of(member);
  }
  public MemberReviewRateStatistics analysis(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    List<Review> reviews = reviewRepository.findReviewsByMemberId(memberId);

    Map<Double,Long> statistics =createStatistics();
    for(Review r: reviews){
      Double key = getKeyFromStarRating(r.getStarRating());
      Long cnt = statistics.get(key);
      statistics.put(key,cnt+1);
    }
    TreeMap<Double,Long> sortedStatistics = new TreeMap<>(statistics);
    return new MemberReviewRateStatistics(sortedStatistics);
  }


  public MusicalRateStatistics analysis(String musicalId){
    List<Review> reviews = reviewRepository.findReviewsByMusicalId(musicalId);
    Map<Double,Long> statistics =createStatistics();
    for(Review r : reviews){
      Double key = getKeyFromStarRating(r.getStarRating());
      Long cnt = statistics.get(key);
      statistics.put(key,cnt+1);
    }
    TreeMap<Double,Long> sortedStatistics = new TreeMap<>(statistics);
    return new MusicalRateStatistics(sortedStatistics);
  }
  private Double getKeyFromStarRating(Double starRating){
    for(int i=0;i<10;i++){
      if(starRating>=(5.0-i*0.5))
        return 5.0-i*0.5;
    }
    return 0.5D;
  }
  private Map<Double,Long> createStatistics(){
    Map<Double,Long> statistics = new HashMap<>();
    for(int i=1;i<=10;i++){
      statistics.put(0.5D*i,0L);
    }
    return statistics;
  }
}
