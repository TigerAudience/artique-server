package com.artique.api.test.resolver;

import com.artique.api.resolver.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResolverTestController {
  @GetMapping("/test/resolver")
  public String getMapping(@LoginUser String id){
    return id;
  }
}
