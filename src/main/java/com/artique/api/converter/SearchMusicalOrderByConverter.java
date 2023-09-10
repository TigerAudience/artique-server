package com.artique.api.converter;

import com.artique.api.search.MusicalOrderBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchMusicalOrderByConverter implements Converter<String, MusicalOrderBy> {
  @Override
  public MusicalOrderBy convert(String source) {return MusicalOrderBy.of(source);}
}
