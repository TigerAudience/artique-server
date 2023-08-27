package com.artique.api.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

  @InjectMocks
  private MemberController memberController;

  @Mock
  private MemberService memberService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init(){
    mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
  }

  @Test
  @DisplayName("duplicated controller 성공 테스트")
  void duplicated_success() throws Exception{
    // given
    when(memberService.checkDuplicateMember("sample_id")).thenReturn(true);

    // when
    ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/member/duplicate").param("member-id","sample_id")
    );

    //then
    ResultActions mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("memberId","sample_id").exists());

  }
}
