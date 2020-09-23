package com.example.userauthenticationjwt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A test API to add behind the authentication wall
 */
@Controller
public class HelloResource {

  @RequestMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }
}
