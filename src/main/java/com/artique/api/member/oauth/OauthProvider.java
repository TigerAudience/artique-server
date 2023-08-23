package com.artique.api.member.oauth;

import com.artique.api.member.dto.OauthMember;
import org.springframework.stereotype.Component;

@Component
public interface OauthProvider {
  OauthMember getUserFromThirdParty(String accessToken);
  boolean canSupport(String thirdParty);
}
