package com.artique.api.report;

import com.artique.api.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report,Long> {
  @Modifying
  @Query(value = "delete from Report r where r.review.id=:review_id")
  void deleteAllByReviewId(@Param(value = "review_id")Long reviewId);

  @Modifying
  @Query(value = "delete from Report r where r.reportMember.id=:member_id")
  void deleteAllByMemberId(@Param("member_id")String memberId);
}
