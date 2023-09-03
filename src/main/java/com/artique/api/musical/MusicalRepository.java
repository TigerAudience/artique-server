package com.artique.api.musical;

import com.artique.api.entity.Musical;
import com.artique.api.musical.dao.MusicalWithRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MusicalRepository extends JpaRepository<Musical,String> {
  @Query("select new com.artique.api.musical.dao.MusicalWithRating" +
          "(m.id,m.posterUrl,m.name,avg(r.starRating),count(r.id),m.beginDate," +
          "m.endDate,m.placeName,m.runningTime,m.casting,m.plot) from Musical m join m.reviews r " +
          "where m.id = :musicalId group by m.id")
  public MusicalWithRating findMusicalWithRating(@Param(value = "musicalId") String musicalId);

}
