package com.artique.api.feed;

import com.artique.api.entity.Review;
import com.artique.api.musical.dto.MusicalReview;
import org.springframework.data.domain.Page;
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

  @Query(value = "select new com.artique.api.feed.FeedDto(mem.nickname,mem.profileUrl,mem.id," +
          "mus.name,mus.posterUrl,mus.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,t.id)" +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id order by r.thumbsUp")
  Slice<FeedDto> findPageReviewsByMemberSlice(Pageable pageable, @Param("member_id") String memberId);

  @Query(value = "select new com.artique.api.musical.dto.MusicalReview" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id order by r.thumbsUp",
          countQuery = "select count(r.id) from Review r join r.musical mus where mus.id = :musical_id")
  Page<MusicalReview> findPageMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                        @Param("member_id")String memberId);
}
