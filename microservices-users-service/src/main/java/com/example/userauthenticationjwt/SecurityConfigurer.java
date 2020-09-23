package com.example.userauthenticationjwt;

import com.example.userauthenticationjwt.services.CustomUserDetailsService;
import com.example.userauthenticationjwt.util.JWTRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private JWTRequestFilter jwtRequestFilter;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
  }

  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  /**
   * Disable security check for /authenticate
   */
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // @formatter:off
    httpSecurity
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/authenticate").permitAll()
      .anyRequest().authenticated()
      .and().exceptionHandling()
      .and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // @formatter:on

    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
