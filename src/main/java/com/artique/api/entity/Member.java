package com.artique.api.entity;

import com.artique.api.member.exception.UpdateMemberException;
import com.artique.api.member.request.UpdateMemberReq;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    @Id
    private String id;
    private String nickname;
    private String profileUrl;
    private String introduce;
    private String password;
    private ZonedDateTime createdAt;

    public void update(UpdateMemberReq memberForm,String loginMemberId){
        if(!loginMemberId.equals(this.id))
            throw new UpdateMemberException("user do not match.","MEMBER-UPDATE-002");
        this.nickname=memberForm.getNickname();
        this.profileUrl=memberForm.getProfileUrl();
        this.introduce=memberForm.getIntroduce();
        this.password=memberForm.getPassword();
    }
    public void changeImage(String imageUrl){
        this.profileUrl=imageUrl;
    }
}
