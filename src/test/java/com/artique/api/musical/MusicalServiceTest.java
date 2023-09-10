package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.entity.Review;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.musical.dao.MusicalReviewDao;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicalServiceTest {

  @InjectMocks
  @Spy
  private MusicalService musicalService;
  @Mock
  private MusicalRepository musicalRepository;
  @Mock
  private ReviewRepository reviewRepository;

  @Test
  @DisplayName("getDetail 성공 [리뷰가 하나도 없을 때]")
  void getDetail_success_no_reviews(){
    //given
    Musical musical = createSampleMusical();
    when(musicalRepository.findById(anyString())).thenReturn(Optional.of(musical));
    when(musicalRepository.findMusicalWithRating(anyString())).thenReturn(null);

    //when
    MusicalInfo musicalInfo = musicalService.getDetail(musical.getId());

    //then
    String average = String.format("%.1f",0D)+" ("+ 0L +")";
    MusicalInfo expectResult = new MusicalInfo(musical.getId(),musical.getPosterUrl(),musical.getName(),average,null,
            musical.getPlaceName(),musical.getRunningTime(),musical.getCasting(),musical.getPlot());
    assertThat(musicalInfo).usingRecursiveComparison().isEqualTo(expectResult);
  }
  @Test
  @DisplayName("getDetail 성공 [리뷰가 있을 때]")
  void getDetail_success_reviews(){
    //given
    Musical musical = createSampleMusical();
    MusicalWithRating musicalWithRating = createSampleMusicalWithRating(musical);
    when(musicalRepository.findById(anyString())).thenReturn(Optional.of(musical));
    when(musicalRepository.findMusicalWithRating(anyString())).thenReturn(musicalWithRating);

    //when
    MusicalInfo musicalInfo = musicalService.getDetail(musical.getId());

    //then
    String average = String.format("%.1f",3.3D)+" ("+ 5L +")";
    MusicalInfo expectResult = new MusicalInfo(musical.getId(),musical.getPosterUrl(),musical.getName(),average,null,
            musical.getPlaceName(),musical.getRunningTime(),musical.getCasting(),musical.getPlot());
    assertThat(musicalInfo).usingRecursiveComparison().isEqualTo(expectResult);
  }
  @Test
  @DisplayName("getDetail 실패 [DB에 없는 musical id일 때]")
  void getDetail_failed_invalid_musicalId(){
    //given
    when(musicalRepository.findById(anyString())).thenReturn(Optional.empty());

    //when
    MusicalException exception = assertThrows(MusicalException.class,
            ()->musicalService.getDetail("sample-id"));

    //then
    assertThat(exception.getErrorCode()).isEqualTo("MUSICAL-001");
  }

  @Test
  @DisplayName("getReviews(smallList) 성공")
  void getReviews_smallList_success(){
    //given
    List<MusicalReviewDao> musicals = new ArrayList<>();
    for(int i=0;i<3;i++){
      musicals.add(new MusicalReviewDao("member"+i,"image "+i,"member-id "+i,
              null,3.0,(long)50-i*5,"short",(long)i, ZonedDateTime.now(),null));
    }
    Page<MusicalReviewDao> musicalReviewDaos = new PageImpl<>(musicals, Pageable.ofSize(3),20);
    when(reviewRepository.findPageMusicalReviewsByMusicalId(any(),anyString(),nullable(String.class))).thenReturn(musicalReviewDaos);

    //when
    MusicalReviewSmallList musicalReviews = musicalService.getReviews(null,"sample-musical-id");

    //then
    long tc = 50L;
    for (MusicalReview r : musicalReviews.getReviews()){
      assertThat(r.getThumbsCount()).isEqualTo(tc);
      tc-=5;
      assertThat(r.getIsThumbsUp()).isFalse();
    }
    assertThat(musicalReviews.getTotalReviewCount()).isEqualTo(20);
  }

  @Test
  @DisplayName("getReviews(all) 성공")
  void getReviews_all_success(){
    //given
    List<MusicalReviewDao> musicals = new ArrayList<>();
    for(int i=0;i<3;i++){
      musicals.add(new MusicalReviewDao("member"+i,"image "+i,"member-id "+i,
              null,3.0,(long)50-i*5,"short",(long)i, ZonedDateTime.now(),null));
    }
    Slice<MusicalReviewDao> musicalReviewDaos = new SliceImpl<>(musicals,Pageable.ofSize(10),true);
    when(reviewRepository.findMusicalReviewsByMusicalId(any(),anyString(),nullable(String.class))).thenReturn(musicalReviewDaos);

    //when
    MusicalReviewSlice result = musicalService
            .getReviews(null,"sample-musical-id",0,10,ReviewOrderBy.THUMBS);

    //then
    verify(reviewRepository,times(1))
            .findMusicalReviewsByMusicalId(eq(PageRequest.of(0,10,
                    Sort.by(Sort.Direction.DESC,ReviewOrderBy.THUMBS.getFieldName()))),anyString(),nullable(String.class));
    for(MusicalReview r : result.getReviews()){
      assertThat(r.getIsThumbsUp()).isFalse();
    }
    assertThat(result.getSize()).isEqualTo(10);
    assertThat(result.isHasNext()).isTrue();
  }

  @Test
  @DisplayName("analysis 성공")
  void analysis_success(){
    //given
    List<Review> reviews = new ArrayList<>();
    for(int i=1;i<=10;i++){
      Review r = new Review((long)i,i>=5?i*0.5D:1.0,null,null,null,
              null,null,null,null,null,null,null);
      reviews.add(r);
    }
    reviews.add(new Review(11L,0D,null,null,null,
            null,null,null,null,null,null,null));
    when(reviewRepository.findReviewsByMusicalId(anyString())).thenReturn(reviews);
    //when
    TreeMap<Double,Long> result = musicalService.analysis("sample-musical-id").getStatistic();

    //then
    TreeMap<Double,Long> expectedMap = new TreeMap<>();
    for(int i=1;i<=10;i++){
      expectedMap.put(0.5D*i,i>=5?1L:0.5D*i==1D?4L:0L);
    }
    expectedMap.put(0.5D,1L);
    Double sortedKey = 0.5D;
    for(Map.Entry<Double,Long> entry : result.entrySet()){
      Long expectedLong = expectedMap.get(entry.getKey());
      assertThat(entry.getValue()).isEqualTo(expectedLong);
      assertThat(entry.getKey()).isEqualTo(sortedKey);
      sortedKey+=0.5;
    }
    assertThat(result.size()).isEqualTo(10);

  }

  private Musical createSampleMusical(){
    return new Musical("id","sample-name",null,null,"sample-placeName","sample-genre","sample-casting","sample-runningTime",
            "sample-plot","sample-posterUrl","sample-musicalStatus",null);
  }
  private MusicalWithRating createSampleMusicalWithRating(Musical m){
    return new MusicalWithRating(m.getId(),m.getPosterUrl(),m.getName(),3.3D,5L,
            null,null,m.getPlaceName(),m.getRunningTime(),m.getCasting(),m.getPlot());
  }
}
