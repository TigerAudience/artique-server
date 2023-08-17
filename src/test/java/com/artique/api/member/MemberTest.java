package com.artique.api.member;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import com.artique.api.member.response.MemberDuplicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberTest {

  @InjectMocks
  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @Test
  @DisplayName("중복 없는 아이디 테스트")
  void validateId(){
    //given
    String memberId = "sample_member_id";
    MemberDuplicate memberDuplicate = new MemberDuplicate(memberId);
    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    //when
    MemberDuplicate findMember  = memberService.checkDuplicateMember(memberId);

    //then
    assertThat(findMember.getMemberId()).isEqualTo(memberDuplicate.getMemberId());
  }

  @Test
  @DisplayName("증복 아이디 테스트")
  void name(){
    //given
    String memberId = "sample_member_id";
    Member initMember = new Member(memberId,"sample_nickname","sample_profileUrl",
            "sample_introduce","sample_password");
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(initMember));

    //when
    LoginException exception = Assertions.assertThrows(LoginException.class,
            ()->memberService.checkDuplicateMember(memberId));
    //then
    LoginExceptionCode code = LoginExceptionCode.DUPLICATE_LOGIN_ID;
    Assertions.assertEquals(exception.getErrorCode(), code.toString());

  }

}
