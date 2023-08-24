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
  @Value("${jwt.jwk.n}")
  private String nStr;
  @Value("${jwt.jwk.e}")
  private String eStr;
  private PublicKey publicKey;
  @PostConstruct
  public void initPublicKey()throws InvalidKeySpecException, NoSuchAlgorithmException {
    publicKeyGeneration();
  }

  @Override
  public OauthMember getUserFromThirdParty(String jwt) {
    try {
      Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwt).getBody();
      Long id = claims.get("sub",Long.class);
      return OauthMember.of(new GoogleUser(id));
    } catch (JwtException jwtException) {
      throw LoginException.builder()
              .message("invalid jwt : "+jwtException.getMessage())
              .errorCode(OauthExceptionCode.INVALID_JWT.toString())
              .build();
    }
  }

  private void publicKeyGeneration()throws NoSuchAlgorithmException,InvalidKeySpecException{
    byte[] eDec = Base64Utils.decodeFromString(eStr);
    byte[] nDec = Base64Utils.decodeFromUrlSafeString(nStr);
    BigInteger e = new BigInteger(1,eDec);
    BigInteger n = new BigInteger(1,nDec);

    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    this.publicKey = keyFactory.generatePublic(publicKeySpec);
  }

  @Override
  public boolean canSupport(String thirdParty) {
    return thirdParty.equals("google");
  }
}
