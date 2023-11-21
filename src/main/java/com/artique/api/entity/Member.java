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
import java.util.UUID;

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
    private Integer warningCount;
    private Integer reportCount;
    private ZonedDateTime banDate;
    private ZonedDateTime createdAt;

    public void update(UpdateMemberReq memberForm,String loginMemberId){
        if(!loginMemberId.equals(this.id))
            throw new UpdateMemberException("user do not match.","MEMBER-UPDATE-002");
        if(memberForm.getNickname()!=null)
            this.nickname=memberForm.getNickname();
        if(memberForm.getProfileUrl()!=null)
            this.profileUrl=memberForm.getProfileUrl();
        if (memberForm.getIntroduce()!=null)
            this.introduce=memberForm.getIntroduce();
        if (memberForm.getPassword()!=null)
            this.password=memberForm.getPassword();
    }
    public void changeImage(String imageUrl){
        this.profileUrl=imageUrl;
    }
    public void increaseReportCount(){
        this.reportCount+=1;
    }
    public String updateToRandomPassword(){
        String randomUUID = UUID.randomUUID().toString().substring(0,8);
        this.password=seahnHash(randomUUID);
        return randomUUID;
    }
    private String seahnHash(String s){
        int a = 1, c = 0, h, o;
        if (s != null) {
            a = 0;
            for (h = s.length() - 1; h >= 0; h--) {
                o = s.charAt(h);
                a = (a << 6 & 268435455) + o + (o << 14);
                c = a & 266338304;
                a = c != 0 ? a ^ c >> 21 : a;
            }
        }
        return String.valueOf(a);
    }
    /** 프런트측 해시 생성 함수
     * export default function hash (s) {
     *     let a = 1, c = 0, h, o;
     *     if (s) {
     *         a = 0;
     *         for (h = s.length - 1; h >= 0; h--) {
     *             o = s.charCodeAt(h);
     *             a = (a << 6 & 268435455) + o + (o << 14);
     *             c = a & 266338304;
     *             a = c !== 0 ? a ^ c >> 21 : a;
     *         }
     *     }
     *     return String(a);
     * }
     */
    public boolean isCorrectPassword(String givenPassword){
        if(givenPassword==null)
            return true;
        return this.password.equals(givenPassword);
    }
}
