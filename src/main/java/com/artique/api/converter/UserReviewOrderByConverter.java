package com.artique.api.converter;

import com.artique.api.profile.userReview.UserReviewOrderBy;
import com.artique.api.search.MusicalOrderBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserReviewOrderByConverter implements Converter<String, UserReviewOrderBy> {
  @Override
  public UserReviewOrderBy convert(String source) {return UserReviewOrderBy.of(source);}
}
