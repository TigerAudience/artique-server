package com.artique.api.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PublicKeyParams {
  private String n;
  private String e;

  public static PublicKeyParams of(Jwk jwk){
    return new PublicKeyParams(jwk.getN(),jwk.getE());
  }
}
