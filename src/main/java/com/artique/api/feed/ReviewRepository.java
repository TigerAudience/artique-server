package com.artique.api.feed;

import com.artique.api.entity.Review;
import com.artique.api.feed.dao.FeedShortsDao;
import com.artique.api.musical.dao.MusicalReviewDao;
import com.artique.api.profile.userReview.dto.ReviewThumb;
import com.artique.api.profile.userReview.dto.UserCreateReview;
import com.artique.api.profile.userReview.dto.UserThumbsReview;
import com.artique.api.reviewDetail.ReviewService;
import com.artique.api.reviewDetail.dto.ReviewDetailThumbsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

  @Query(value = "SELECT new com.artique.api.feed.dao.FeedShortsDao(mem.nickname,mem.profileUrl,mem.id," +
          "mus.name,mus.posterUrl,r.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.shortSpoiler)" +
          "FROM Review r JOIN r.musical mus JOIN r.member mem " +
          "WHERE r.createdAt BETWEEN :begin_date AND :end_date")
  Slice<FeedShortsDao> findPageReviewsByMemberSlice(Pageable pageable,
                                                    @Param("begin_date") ZonedDateTime beginDate,
                                                    @Param("end_date") ZonedDateTime endDate);
  @Query(value = "SELECT new com.artique.api.feed.dao.FeedShortsDao(mem.nickname,mem.profileUrl,mem.id," +
          "mus.name,mus.posterUrl,r.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.shortSpoiler)" +
          "FROM Review r JOIN r.musical mus JOIN r.member mem ")
  Slice<FeedShortsDao> findPageReviewsByMemberSliceAllDate(Pageable pageable);

  @Query(value = "select new com.artique.api.musical.dao.MusicalReviewDao" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.createdAt,r.shortSpoiler,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id")
  Page<MusicalReviewDao> findPageMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                           @Param("member_id")String memberId);

  @Query(value = "select r from Review r where r.musical.id = :musical_id")
  List<Review> findReviewsByMusicalId(@Param("musical_id")String musicalId);

  @Query(value = "select new com.artique.api.musical.dao.MusicalReviewDao" +
          "(mem.nickname,mem.profileUrl,mem.id,r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.id,r.createdAt,r.shortSpoiler,t.id) " +
          "from Review r join r.musical mus join r.member mem " +
          "left join r.thumbs t on t.member.id = :member_id " +
          "where mus.id = :musical_id")
  Slice<MusicalReviewDao> findMusicalReviewsByMusicalId(Pageable pageable, @Param("musical_id")String musicalId,
                                                        @Param("member_id")String memberId);

  @Query(value = "select r from Review r join fetch r.member mem join fetch r.musical mus where r.id = :review_id")
  Optional<Review> findReviewByIdJoinFetchMemberMusical(@Param("review_id")Long reviewId);

  @Query(value = "select new com.artique.api.profile.userReview.dto.UserCreateReview" +
          "(mem.nickname,mem.profileUrl,mem.id, mus.name,mus.posterUrl,r.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.shortSpoiler,r.id) " +
          "from Review r join r.musical mus join r.member mem where r.member.id = :member_id")
  Slice<UserCreateReview> findUserReviewsByMemberId(Pageable pageable, @Param("member_id")String memberId);
  @Query(value = "select new com.artique.api.profile.userReview.dto.UserCreateReview" +
          "(mem.nickname,mem.profileUrl,mem.id, mus.name,mus.posterUrl,r.casting,mus.id," +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.shortSpoiler,r.id) " +
          "from Review r join r.musical mus join r.member mem where r.member.id = :member_id and " +
          "(mus.name like %:keyword%)")
  Slice<UserCreateReview> findUserReviewsByMemberIdAndKeyword(Pageable pageable, @Param("member_id")String memberId,
                                                              @Param("keyword")String keyword);

  @Query(value = "select new com.artique.api.profile.userReview.dto.UserThumbsReview" +
          "(r_mem.nickname,r_mem.profileUrl,r_mem.id, mus.name,mus.posterUrl,r.casting,mus.id, " +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.shortSpoiler,r.id) from Review r join r.musical mus " +
          "join r.thumbs t join r.member r_mem join t.member mem " +
          "where mem.id = :member_id order by t.createdAt desc")
  Slice<UserThumbsReview> findUserReviewsByThumbs(Pageable pageable, @Param("member_id")String memberId);


  @Query(value = "select new com.artique.api.profile.userReview.dto.UserThumbsReview" +
          "(r_mem.nickname,r_mem.profileUrl,r_mem.id, mus.name,mus.posterUrl,r.casting,mus.id, " +
          "r.viewDate,r.starRating,r.thumbsUp,r.shortReview,r.shortSpoiler,r.id) from Review r join r.musical mus " +
          "join r.thumbs t join r.member r_mem join t.member mem " +
          "where mem.id =:member_id and (mus.name like %:keyword%) order by t.createdAt desc")
  Slice<UserThumbsReview> findUserReviewsByThumbsAndKeyword(Pageable pageable, @Param("member_id")String memberId,
                                                            @Param("keyword")String keyword);

  @Query(value = "select new com.artique.api.profile.userReview.dto.ReviewThumb" +
          "(r.id,t.id) from Review r join r.thumbs t join t.member mem on t.member.id=mem.id" +
          " where mem.id=:member_id and r.id in(:rids)")
  List<ReviewThumb> findThumbsByReviewIds(@Param("rids")List<Long> rIds,@Param("member_id")String memberId);

  @Query(value = "select r from Review r join r.member mem where mem.id =:member_id")
  List<Review> findReviewsByMemberId(@Param("member_id")String memberId);

  @Modifying
  @Query(value = "delete from Review r where r.member.id = :member_id")
  void deleteAllByMemberId(@Param(value = "member_id")String memberId);

  @Query(value = "select new com.artique.api.reviewDetail.dto.ReviewDetailThumbsInfo(t.id)" +
          " from Review r join r.thumbs t join t.member mem where mem.id=:member_id")
  List<ReviewDetailThumbsInfo> findThumbsByMemberId(@Param("member_id")String memberId);
}
