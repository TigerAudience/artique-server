package com.artique.api.test.resolver;

import com.artique.api.resolver.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResolverTestController {
  @GetMapping("/test/resolver")
  public String testResolver(@LoginUser String id){
    return id;
  }
  @GetMapping("/test/resolver/supports-parameter/annotation")
  public String testResolverSupportsParameter(String id){return id;}
  @GetMapping("/test/resolver/supports-parameter/type")
  public Long testResolverSupportsParameter(@LoginUser Long id){return id;}
}
