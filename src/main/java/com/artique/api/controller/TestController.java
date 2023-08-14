package com.artique.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/hello-world")
  public String helloWorld(){
    return "hello world!";
  }
}
