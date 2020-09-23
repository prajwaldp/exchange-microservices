package com.example.userauthenticationjwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A test API to add behind the authentication wall
 */
@RestController
public class HelloResource {

  @GetMapping
  public String hello() {
    return "Hello, World!";
  }
}
