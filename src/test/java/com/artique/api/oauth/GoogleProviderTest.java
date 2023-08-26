package com.artique.api.oauth;

import com.artique.api.member.oauth.GoogleProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GoogleProviderTest {
  @InjectMocks
  private GoogleProvider googleProvider;

  @Test
  @DisplayName("google provider 성공 테스트")
  void success_google_provider(){
  }
}
