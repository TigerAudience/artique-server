package com.artique.api.session;

import com.artique.api.entity.Member;
import com.artique.api.member.exception.LoginException;
import jakarta.persistence.Id;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SessionTest {
  private final CustomSession session = new MemorySession();

  @Test
  @DisplayName("세션 생성 테스트")
  void create_session(){
    //given
    Member member = new Member("sample_id","sample_nickname",
            "sample_url","sample_introduce","sample_password",ZonedDateTime.now());

    //when
    String sessionId = session.createSession(member);

    //then
    //"세션이 정상적으로 만들어지는가"를 테스트하는 단위테스트이다. Assertion을 할 요소가 없는 것 같다. 결과를 확인할 수 있는 방안이 없다.
  }

  @Test
  @DisplayName("세션 유효성 테스트")
  void validate_session(){
    //given
    Member member = new Member("sample_id","sample_nickname",
            "sample_url","sample_introduce","sample_password", ZonedDateTime.now());
    String sessionId = session.createSession(member);
    int maxSessionCount = 30;

    //when
    for(int i=0;i<maxSessionCount;i++) {
      if(!session.validateSessionId(sessionId))
        Assertions.fail("유효한 세션이어야 합니다.");
    }
    if(session.validateSessionId(sessionId))
      Assertions.fail("유효하지 않은 세션이어야 합니다.(최대 인증 횟수 초과)");
  }

  @Test
  @DisplayName("회원 ID 추출 테스트")
  void get_memberId(){
    //given
    Member member = new Member("sample_id","sample_nickname",
            "sample_url","sample_introduce","sample_password",ZonedDateTime.now());
    String sessionId = session.createSession(member);

    //when
    String memberId = session.getMemberId(sessionId);

    //then
    Assertions.assertThat(memberId).isEqualTo(member.getId());
  }

}
