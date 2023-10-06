package com.artique.api.profile.summary;

import com.artique.api.entity.Member;
import com.artique.api.member.MemberRepository;
import com.artique.api.profile.summary.response.MemberSummary;
import com.artique.api.profile.userReview.ProfileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

  private final MemberRepository memberRepository;
  public MemberSummary getSummary(String memberId){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(()->new ProfileException("invalid member id","PROFILE-001"));

    return MemberSummary.of(member);
  }
}
