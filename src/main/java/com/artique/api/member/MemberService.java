package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginErrorCode;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.response.MemberDuplicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberDuplicate checkDuplicateMember(String memberId){
    Optional<Member> member = memberRepository.findById(memberId);
    if (member.isEmpty()){
      return new MemberDuplicate(memberId);
    }else{
      throw new LoginException("member id duplicate exception",String.valueOf(LoginErrorCode.DUPLICATE_LOGIN_ID));
    }
  }
}
