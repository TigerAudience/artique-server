package com.artique.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/hello-world")
  @Operation(summary = "예시 컨트롤러")
  public String helloWorld(@CookieValue(name = "session-id")Cookie cookie){
    return "hello world!"+cookie.getValue();
  }
}
