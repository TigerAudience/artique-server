package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.feed.ReviewRepository;
import com.artique.api.musical.dao.MusicalReviewDao;
import com.artique.api.musical.dao.MusicalWithRating;
import com.artique.api.musical.dto.MusicalInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

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

  /*
  @Test
  @DisplayName("getReviews(smallList) 성공")
  void name(){
    //given
    //Page<MusicalReviewDao> musicalReviewDaos =
    //when(reviewRepository.findPageMusicalReviewsByMusicalId(any(),anyString(),anyString())).thenReturn()

    //when


    //then
  }
  */

  private Musical createSampleMusical(){
    return new Musical("id","sample-name",null,null,"sample-placeName","sample-genre","sample-casting","sample-runningTime",
            "sample-plot","sample-posterUrl","sample-musicalStatus",null);
  }
  private MusicalWithRating createSampleMusicalWithRating(Musical m){
    return new MusicalWithRating(m.getId(),m.getPosterUrl(),m.getName(),3.3D,5L,
            null,null,m.getPlaceName(),m.getRunningTime(),m.getCasting(),m.getPlot());
  }
}
