package com.artique.api.test.redis;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {
  @GetMapping("/test/redis")
  public String tryRedis(@RequestParam(value = "name")String name, HttpSession httpSession){
    httpSession.setAttribute("user_name",name);
    return "ok";
  }
}
