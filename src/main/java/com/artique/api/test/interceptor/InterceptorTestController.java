package com.artique.api.test.interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterceptorTestController {
  @GetMapping("/test/interceptor")
  public String testInterceptor(){return "interceptor";}
}
