package com.artique.api.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Jwk{
  private String use;
  private String kid;
  private String n;
  private String e;
  private String alg;
  private String kty;

}