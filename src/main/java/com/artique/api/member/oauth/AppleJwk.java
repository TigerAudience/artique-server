package com.artique.api.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppleJwk {
  private String kty;
  private String kid;
  private String use;
  private String alg;
  private String n;
  private String e;
}
