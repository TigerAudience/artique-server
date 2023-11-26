package com.artique.api.profile.userReview;

import com.artique.api.musical.MusicalException;
import org.springframework.data.domain.Sort;

public enum UserReviewOrderBy {

  THUMBS("thumbs","thumbsUp", Sort.Direction.DESC),
  CREATE("create-date","createdAt", Sort.Direction.DESC);

  private final String orderName;
  private final String fieldName;
  private final Sort.Direction sortDirection;
  UserReviewOrderBy(String orderName, String fieldName,Sort.Direction direction){
    this.orderName=orderName;
    this.fieldName=fieldName;
    this.sortDirection=direction;
  }

  public String getFieldName(){
    return this.fieldName;
  }
  public String getOrderName(){
    return this.orderName;
  }
  public Sort.Direction getDirection(){return this.sortDirection;}

  public static UserReviewOrderBy of(String str){
    if(str==null)
      return UserReviewOrderBy.THUMBS;
    for(UserReviewOrderBy o : UserReviewOrderBy.values()){
      if(o.orderName.equals(str))
        return o;
    }
    throw new MusicalException("invalid orderBy name","PROFILE-010");
  }
  public static Sort sortBy(UserReviewOrderBy orderBy){
    return Sort.by(orderBy.getDirection(),orderBy.getFieldName());
  }
}
