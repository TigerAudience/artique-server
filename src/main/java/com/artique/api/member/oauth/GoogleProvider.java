package com.artique.api.member.oauth;

import com.artique.api.member.dto.GoogleUser;
import com.artique.api.member.dto.OauthMember;
import com.artique.api.member.exception.LoginException;
import com.artique.api.member.exception.OauthExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;


@Component
@RequiredArgsConstructor
public class GoogleProvider implements OauthProvider{
  private final JwtParser jwtParser;

  @Override
  public OauthMember getUserFromThirdParty(String jwt) {
    try {
      Claims claims = jwtParser.parseJwt("google",jwt);
      String idString = claims.get("sub",String.class);
      return OauthMember.of(new GoogleUser(idString));
    } catch (JwtException jwtException) {
      throw LoginException.builder()
              .message("invalid jwt : "+jwtException.getMessage())
              .errorCode(OauthExceptionCode.INVALID_JWT.toString())
              .build();
    }
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return thirdParty.equals("google");
  }
}
