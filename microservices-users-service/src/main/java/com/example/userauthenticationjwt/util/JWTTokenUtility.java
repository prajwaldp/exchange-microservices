package com.example.userauthenticationjwt.util;

import org.springframework.security.core.userdetails.UserDetails;

public class JWTTokenUtility {
  public static String generateToken(UserDetails userDetails) {
    return "OK"; // TODO
  }
}
