package com.artique.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(mappedBy = "musical")
    private List<Review> reviews;
}
