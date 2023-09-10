package com.artique.api.converter;

import com.artique.api.musical.ReviewOrderBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MusicalReviewOrderByConverter implements Converter<String, ReviewOrderBy> {
  @Override
  public ReviewOrderBy convert(String source) {
    return ReviewOrderBy.of(source);
  }
}
