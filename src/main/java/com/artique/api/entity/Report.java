package com.artique.api.entity;

import com.artique.api.report.dto.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private ReportType type;
  @ManyToOne
  private Member reportMember;
  @ManyToOne
  private Review review;
  private LocalDateTime createdAt;
}
