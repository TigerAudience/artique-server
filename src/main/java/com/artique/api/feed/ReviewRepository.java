package com.artique.api.feed;

import com.artique.api.entity.Review;
import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.musical.dao.MusicalReviewDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

  @Query(value = "select new com.artique.api.feed.dao.FeedShortsDao(mem.nickname,mem.profileUrl,mem.id," +
          "mus.name,mus.posterUrl,mus.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,t.id)" +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id order by r.thumbsUp")
  Slice<FeedShortsDao> findPageReviewsByMemberSlice(Pageable pageable, @Param("member_id") String memberId);

  @Query(value = "select new com.artique.api.musical.dao.MusicalReviewDao" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id order by r.thumbsUp")
  Page<MusicalReviewDao> findPageMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                           @Param("member_id")String memberId);

  @Query(value = "select r from Review r where r.musical.id = :musical_id")
  List<Review> findReviewsByMusicalId(@Param("musical_id")String musicalId);
}
