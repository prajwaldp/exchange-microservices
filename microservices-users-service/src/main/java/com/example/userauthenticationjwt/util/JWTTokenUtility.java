package com.example.userauthenticationjwt.util;

import org.springframework.security.core.userdetails.UserDetails;

public class JWTTokenUtility {
  public static String generateToken(UserDetails userDetails) {
    return "OK"; // TODO
  }

  public static String extractUsername(String jwt) {
    // TODO
    return "foo";
  }

  public static boolean validateToken(String jwt, UserDetails userDetails) {
    // TODO
    return false;
  }
}
