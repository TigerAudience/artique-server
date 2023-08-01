package com.artique.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Musical {
    @Id
    private String id;
    private String name;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String placeName;
    private String genre;
    private String casting;
    private Integer runningTime;
    @Column(columnDefinition = "TEXT")
    private String plot;
    private String posterUrl;
}
