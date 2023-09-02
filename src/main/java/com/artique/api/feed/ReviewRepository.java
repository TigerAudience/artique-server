package com.artique.api.feed;

import com.artique.api.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review,Long> {
  @Query(value = "select r from Review r join fetch r.musical mus join fetch r.member mem")
  Slice<Review> findPageReviewBySlice(Pageable pageable);
  @Query(value = "select r from Review r join fetch r.musical mus join fetch r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id")
  Slice<Review> findPageReviewBySliceWithUser(Pageable pageable, @Param("member_id") String memberId);
}
