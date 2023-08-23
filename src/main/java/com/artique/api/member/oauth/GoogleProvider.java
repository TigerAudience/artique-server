package com.artique.api.member.oauth;

import com.artique.api.member.dto.OauthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleProvider implements OauthProvider{
  private final String thirdParty = "google";
  private final ThirdPartyConnector connector;

  @Override
  public OauthMember getUserFromThirdParty(String token) {
    return null;
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return this.thirdParty.equals(thirdParty);
  }
}
