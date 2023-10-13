package com.artique.api.intertceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestRepository {
  private final ThreadLocal<HttpServletRequest> requests = new ThreadLocal<>();
  public HttpServletRequest get(){
    return requests.get();
  }
  public void save(HttpServletRequest request){
    requests.set(request);
  }
  public void delete(){
    requests.remove();
  }
}
