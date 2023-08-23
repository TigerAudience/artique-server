package com.artique.api.member.dto;

import com.artique.api.entity.Member;
import com.artique.api.member.request.JoinMemberReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class OauthMember {
  private String id;
  private String thirdParty;

  public static JoinMemberReq toJoinMemberReq(OauthMember oauthMember){
    return new JoinMemberReq(oauthMember.getId(), null);
  }

  public static OauthMember of(KakaoUser kakaoUser){
    return new OauthMember("kakao@"+kakaoUser.getId(),"kakao");
  }
  public static OauthMember of(GoogleUser googleUser){
    return new OauthMember("google@"+googleUser.getId(),"google");
  }
}
