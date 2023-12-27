package com.artique.api.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Musical {
    @Id
    private String id;
    private String name;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String placeName;
    private String genre;
    private String casting;
    private String runningTime;
    @Column(columnDefinition = "TEXT")
    private String plot;
    private String posterUrl;
    private String musicalStatus;
    private String stickyName;
    @OneToMany(mappedBy = "musical")
    private List<Review> reviews;
}
