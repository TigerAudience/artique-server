package com.artique.api.converter;

import com.artique.api.musical.OrderBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MusicalReviewOrderByConverter implements Converter<String, OrderBy> {
  @Override
  public OrderBy convert(String source) {
    return OrderBy.of(source);
  }
}
