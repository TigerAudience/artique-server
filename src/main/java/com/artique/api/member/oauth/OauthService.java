package com.artique.api.member.oauth;

import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.LoginExceptionCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
  private final List<OauthProvider> providers;

  public OauthMember getOauthMember(String thirdParty,String accessToken){
    for(OauthProvider provider : providers){
      if(provider.canSupport(thirdParty))
        return provider.getUserFromThirdParty(accessToken);
    }
    throw LoginException.builder()
            .message("invalid third party name : "+thirdParty)
            .errorCode(LoginExceptionCode.INVALID_THIRD_PARTY_NAME.toString())
            .build();
  }
}
