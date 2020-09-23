package com.example.userauthenticationjwt;

import com.example.userauthenticationjwt.services.CustomUserDetailsService;
import com.example.userauthenticationjwt.util.JWTTokenUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A test API to add behind the authentication wall
 */
@RestController
public class HelloResource {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @GetMapping
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping("/authenticate")
  public String authenticate(@RequestParam(value = "username") String username,
      @RequestParam(value = "password") String password) throws Exception {
    try {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final String jwt = JWTTokenUtility.generateToken(userDetails);
    return jwt;
  }
}
