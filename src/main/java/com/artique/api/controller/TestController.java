package com.artique.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/hello-world")
  @Operation(summary = "예시 컨트롤러")
  public String helloWorld(){
    return "hello world!";
  }
}
