package com.artique.api.musical;

public enum OrderBy {
  THUMBS("thumbs","thumbsUp"),VIEW("view-date","viewDate"),
  CREATE("create-date","createdAt");

  private final String orderName;
  private final String fieldName;
  OrderBy(String orderName,String fieldName){
    this.orderName=orderName;
    this.fieldName=fieldName;
  }

  public String getFieldName(){
    return this.fieldName;
  }
  public String getOrderName(){
    return this.orderName;
  }

  public static OrderBy of(String str){
    //str==null 불가능(argumentresolver가 이미 검사하는듯함,
    // MusicalException이 ControllerAdvice에서 안받아짐(Converter가 exception 받아서 새로운 exception 던짐))
    if(str==null)
      return OrderBy.THUMBS;
    for(OrderBy o : OrderBy.values()){
      if(o.orderName.equals(str))
        return o;
    }
    throw new MusicalException("invalid orderBy name","MUSICAL-002");
  }
}
