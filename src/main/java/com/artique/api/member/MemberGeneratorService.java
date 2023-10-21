package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.request.JoinMemberReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class MemberGeneratorService {

  public Member toMember(JoinMemberReq joinMemberReq){
    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    return new Member(joinMemberReq.getMemberId(),
            "임시 닉네임", "임시 url","임시 소개", joinMemberReq.getMemberPW(), dateTime);
  }
}
