package com.artique.api.home;

import com.artique.api.entity.ArtiqueRecommendMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtiqueRecommendMusicalRepository extends JpaRepository<ArtiqueRecommendMusical,Long> {
  @Query("SELECT arm FROM ArtiqueRecommendMusical arm ORDER BY arm.sequence")
  List<ArtiqueRecommendMusical> findAllOrderBySequence();
}
