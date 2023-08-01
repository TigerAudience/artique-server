package com.artique.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    private String id;
    private String nickname;
    private String profileUrl;
    private String introduce;
}
