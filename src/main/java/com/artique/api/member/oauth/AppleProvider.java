package com.artique.api.member.oauth;

import com.artique.api.member.dto.AppleUser;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.OauthExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppleProvider implements OauthProvider{
  private final JwtParser jwtParser;
  @Override
  public OauthMember getUserFromThirdParty(String jwt) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String[] chunks = jwt.split("\\.");
      Base64.Decoder decoder = Base64.getUrlDecoder();
      String header = new String(decoder.decode(chunks[0]));
      JwtHeader jwtHeader = mapper.readValue(header, JwtHeader.class);
      jwtParser.generateApplePublicKey(jwtHeader.getKid());
      Claims claims = jwtParser.parseJwt("apple",jwt);
      String idString = claims.get("sub",String.class);
      return OauthMember.of(new AppleUser(idString));
    } catch (Exception e) {
      throw LoginException.builder()
              .message("invalid jwt : "+e.getMessage())
              .errorCode(OauthExceptionCode.INVALID_JWT.toString())
              .build();
    }
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return thirdParty.equals("apple");
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  private static class JwtHeader{
    private String alg;
    private String kid;
    private String typ;
  }
}
