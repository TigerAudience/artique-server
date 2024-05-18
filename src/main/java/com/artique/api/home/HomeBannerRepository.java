package com.artique.api.home;

import com.artique.api.entity.HomeBanner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeBannerRepository extends JpaRepository<HomeBanner,Long> {
  @Query("SELECT hb FROM HomeBanner hb ORDER BY hb.sequence")
  List<HomeBanner> getHomeBannerOrderBySequence();
}
