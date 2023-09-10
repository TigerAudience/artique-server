package com.artique.api.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PublicKeyParams {
  private String n;
  private String e;

  public static PublicKeyParams of(GoogleJwk googleJwk){
    return new PublicKeyParams(googleJwk.getN(), googleJwk.getE());
  }
  public static PublicKeyParams of(AppleJwk appleJwk){
    return new PublicKeyParams(appleJwk.getN(),appleJwk.getE());
  }
}
