package com.artique.api.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThumbsRepository extends JpaRepository<Thumbs,Long> {
  @Query(value = "select t from Thumbs t where t.member.id=:member_id and t.review.id=:review_id")
  Optional<Thumbs> findThumbsByMemberAndReview(@Param("member_id")String memberId,@Param("review_id")Long reviewId);

  @Modifying
  @Query(value = "delete from Thumbs t where t.review.id = :review_id")
  void deleteAllByReviewIds(@Param("review_id")Long review);

  @Modifying
  @Query(value = "delete from Thumbs t where t.member.id = :member_id")
  void deleteAllByMemberId(@Param("member_id")String memberId);
}
