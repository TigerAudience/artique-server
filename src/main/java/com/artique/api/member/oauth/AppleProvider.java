package com.artique.api.member.oauth;

import com.artique.api.member.dto.AppleUser;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.OauthExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleProvider implements OauthProvider{
  private final JwtParser jwtParser;
  @Override
  public OauthMember getUserFromThirdParty(String jwt) {
    try {
      Claims claims = jwtParser.parseJwt("apple",jwt);
      String idString = claims.get("sub",String.class);
      return OauthMember.of(new AppleUser(idString));
    } catch (JwtException jwtException) {
      throw LoginException.builder()
              .message("invalid jwt : "+jwtException.getMessage())
              .errorCode(OauthExceptionCode.INVALID_JWT.toString())
              .build();
    }
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return thirdParty.equals("apple");
  }
}
