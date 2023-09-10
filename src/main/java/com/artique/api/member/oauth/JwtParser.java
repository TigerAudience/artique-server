package com.artique.api.member.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class JwtParser {
  private ConcurrentHashMap<String, PublicKey> publicKeys = new ConcurrentHashMap<>();
  @Value("${jwt.jwk.google.n}")
  private String googleN;
  @Value("${jwt.jwk.google.e}")
  private String googleE;

  @Value("${jwt.jwk.apple.n}")
  private String appleN;
  @Value("${jwt.jwk.apple.e}")
  private String appleE;
  private final ThirdPartyConnector thirdPartyConnector;
  @PostConstruct
  public void initPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException{
    publicKeys.put("google",publicKeyGeneration(googleE,googleN));
    publicKeys.put("apple",publicKeyGeneration(appleE,appleN));
  }
  public void generateGooglePublicKey(String kid) throws NoSuchAlgorithmException, InvalidKeySpecException {
    PublicKeyParams params = thirdPartyConnector.getPublicKeyParamsFromGoogleServer(kid);
    publicKeys.put("google",publicKeyGeneration(params.getE(),params.getN()));
  }

  private PublicKey publicKeyGeneration(String eStr, String nStr)throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] eDec = Base64Utils.decodeFromString(eStr);
    byte[] nDec = Base64Utils.decodeFromUrlSafeString(nStr);
    BigInteger e = new BigInteger(1,eDec);
    BigInteger n = new BigInteger(1,nDec);

    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePublic(publicKeySpec);
  }

  public Claims parseJwt(String thirdParty, String jwt){
    PublicKey publicKey = publicKeys.get(thirdParty);
    return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwt).getBody();
  }

}
