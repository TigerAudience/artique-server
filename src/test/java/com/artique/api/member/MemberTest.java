package com.artique.api.member;

import com.artique.api.member.response.MemberDuplicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    String memberId = new String("ohmyGosh");
    MemberDuplicate memberDuplicate = new MemberDuplicate(memberId);
    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    //when
    MemberDuplicate findMember  = memberService.checkDuplicateMember(memberId);

    //then
    assertThat(findMember.getMemberId()).isEqualTo(memberDuplicate.getMemberId());
  }

}
