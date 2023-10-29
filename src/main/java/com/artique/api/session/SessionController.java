package com.artique.api.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {
  private final SessionStore sessionStore;
  @GetMapping("/session/all")
  public List<String> findAllSessionUser(){
    return sessionStore.findAll();
  }
}
