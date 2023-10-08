package com.artique.api.profile.userReview;

import com.artique.api.musical.MusicalException;
import com.artique.api.musical.ReviewOrderBy;

public enum UserReviewOrderBy {

  THUMBS("thumbs","thumbsUp"),CREATE("create-date","createdAt");

  private final String orderName;
  private final String fieldName;
  UserReviewOrderBy(String orderName, String fieldName){
    this.orderName=orderName;
    this.fieldName=fieldName;
  }

  public String getFieldName(){
    return this.fieldName;
  }
  public String getOrderName(){
    return this.orderName;
  }

  public static UserReviewOrderBy of(String str){
    if(str==null)
      return UserReviewOrderBy.THUMBS;
    for(UserReviewOrderBy o : UserReviewOrderBy.values()){
      if(o.orderName.equals(str))
        return o;
    }
    throw new MusicalException("invalid orderBy name","PROFILE-010");
  }
}
