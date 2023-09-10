package com.artique.api.search;

import com.artique.api.musical.MusicalException;
import com.artique.api.musical.ReviewOrderBy;

public enum MusicalOrderBy {

  DATE("date"),REVIEW("review");

  private final String orderName;
  MusicalOrderBy(String orderName){
    this.orderName=orderName;
  }

  public String getOrderName(){
    return this.orderName;
  }

  public static MusicalOrderBy of(String str){
    for(MusicalOrderBy o : MusicalOrderBy.values()){
      if(o.getOrderName().equals(str))
        return o;
    }
    throw new MusicalException("invalid orderBy name","MUSICAL-002");
  }
}
