package com.artique.api.member.oauth;

import com.artique.api.member.dto.GoogleUser;
import com.artique.api.member.dto.OauthMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GoogleProvider implements OauthProvider{
  private final String thirdParty = "google";

  @Override
  public OauthMember getUserFromThirdParty(String jwt) {
    String client_id = "1001943377543-q3ed3vrdtg2hhmc8edp88c6eggh48bcs.apps.googleusercontent.com";
    String publicKeyStr = PublicKeyGeneration().toString();

    try {
      // Parse the public key from PEM format
      PublicKey publicKey = parsePublicKey(publicKeyStr);

      // Verify the JWT signature
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(publicKey)
              .build()
              .parseClaimsJws(jwt)
              .getBody();

      // Print the claims
      System.out.println("JWT Claims: " + claims);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return OauthMember.of(new GoogleUser(2119191949L,"haaaa"));
  }
  private PublicKey parsePublicKey(String publicKeyStr) throws Exception {

    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePublic(keySpec);
  }

  private PublicKey PublicKeyGeneration(){
      String eValue = "AQAB";
      String nValue = "qxHzsqeQzXW-LT2Z-k30bJPhoMful1wUVPYUmuk" +
              "RR7qRnsC-7mQYaXkXaiuYcdlsZBS_AzfppQVIJ6GKncXQ" +
              "cZJ7-x-RwRm2exSdbmQ8xPJY1c1BLflc0Qa4fwGY_MjbR1k" +
              "vlcx6etWhsnJqmivX9ALnCF5ZTR4ewC-BH7ZuilUYb6bCgG-zp" +
              "SHNIQpgxO9gE8XoPBujGK9w6v_uzZb4rj2_8KWWT6RRBBQs1KDZm" +
              "xzFkDcVOjgyTLmGPpHLQDF3R02DHzeaB84KB0QM-KyKIK1ejzClj" +
              "dwCPAhNB9r14-01cUI1GUKuhv0tPgne3Je9qPIxl_g2FuZuqBnT1MPo9w";

      try {
        BigInteger e = new BigInteger(eValue);
        BigInteger n = new BigInteger(nValue);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        System.out.println("Generated Public Key: " + publicKey);
        return publicKey;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

  @Override
  public boolean canSupport(String thirdParty) {
    return this.thirdParty.equals(thirdParty);
  }
}
