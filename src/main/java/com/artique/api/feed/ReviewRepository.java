package com.artique.api.feed;

import com.artique.api.entity.Review;
import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.musical.dao.MusicalReviewDao;
import com.artique.api.profile.userReview.ReviewThumb;
import com.artique.api.profile.userReview.UserCreateReview;
import com.artique.api.profile.userReview.UserThumbsReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

  @Query(value = "select new com.artique.api.feed.dao.FeedShortsDao(mem.nickname,mem.profileUrl,mem.id," +
          "mus.name,mus.posterUrl,r.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,t.id)" +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id")
  Slice<FeedShortsDao> findPageReviewsByMemberSlice(Pageable pageable, @Param("member_id") String memberId);

  @Query(value = "select new com.artique.api.musical.dao.MusicalReviewDao" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.createdAt,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id")
  Page<MusicalReviewDao> findPageMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                           @Param("member_id")String memberId);

  @Query(value = "select r from Review r where r.musical.id = :musical_id")
  List<Review> findReviewsByMusicalId(@Param("musical_id")String musicalId);

  @Query(value = "select new com.artique.api.musical.dao.MusicalReviewDao" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.createdAt,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id")
  Slice<MusicalReviewDao> findMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                        @Param("member_id")String memberId);

  @Query(value = "select r from Review r join fetch r.member mem join fetch r.musical mus where r.id = :review_id")
  Optional<Review> findReviewByIdJoinFetchMemberMusical(@Param("review_id")Long reviewId);

  /**
   * user 작성한 reviews
   * @param pageable
   * @param memberId
   * @return
   */
  @Query(value = "select new com.artique.api.profile.userReview.UserCreateReview" +
          "(mus.name,mus.posterUrl,r.casting,mus.id, r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id) " +
          "from Review r join r.musical mus join r.member mem where r.member.id = :member_id")
  Slice<UserCreateReview> findUserReviewsByMemberId(Pageable pageable, @Param("member_id")String memberId);

  @Query(value = "select new com.artique.api.profile.userReview.UserThumbsReview" +
          "(mem.nickname,mem.profileUrl,mem.id, mus.name,mus.posterUrl,r.casting,mus.id, " +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id) from Review r join r.musical mus join r.thumbs t join t.member mem " +
          "where mem.id =:member_id group by r.id")
  Slice<UserThumbsReview> findUserReviewsByThumbs(Pageable pageable, @Param("member_id")String memberId);

  @Query(value = "select new com.artique.api.profile.userReview.ReviewThumb" +
          "(r.id,t.id) from Review r join r.thumbs t join t.member mem on t.member.id=mem.id" +
          " where mem.id=:member_id and r.id in(:rids) group by r.id")
  List<ReviewThumb> findThumbsByReviewIds(@Param("ids")List<Long> rIds);


  @Query(value = "select new com.artique.api.profile.userReview.UserThumbsReview" +
          "(mem.nickname,mem.profileUrl,mem.id, mus.name,mus.posterUrl,r.casting,mus.id, " +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id) from Review r join r.musical mus join r.thumbs t join t.member mem " +
          "where mem.id =:member_id and " +
          "(r.shortReview like %:key_world% or mus.casting like %:key_word% or mus.name like %:key_word%) group by r.id")
  List<UserThumbsReview> searchUserReviewsByThumbs
          (@Param("member_id")String memberId,@Param("key_word")String keyWord);

}
