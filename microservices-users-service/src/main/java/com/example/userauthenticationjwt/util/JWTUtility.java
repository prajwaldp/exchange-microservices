package com.example.userauthenticationjwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtility {

  private static String SECRET_KEY = "secret";

  private static Claims extractAllClaims(String claimsJwt) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(claimsJwt).getBody();
  }

  private static <T> T extractClaim(String claimsJwt, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(claimsJwt);
    return claimsResolver.apply(claims);
  }

  public static String extractUsername(String claimsJwt) {
    return extractClaim(claimsJwt, Claims::getSubject);
  }

  private static Date extractExpiration(String claimsJwt) {
    return extractClaim(claimsJwt, Claims::getExpiration);
  }

  public static String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    // @formatter:off
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
      .compact();
    // @formatter:on
  }

  public static boolean validateToken(String claimsJwt, UserDetails userDetails) {
    final String username = extractUsername(claimsJwt);
    boolean isTokenExpired = extractExpiration(claimsJwt).before(new Date());
    return username.equals(userDetails.getUsername()) && !isTokenExpired;
  }
}
