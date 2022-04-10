package com.paidy.restaurant.authentication;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthenticationConfigAdapter extends WebSecurityConfigurerAdapter {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private BasicAuthUserConfig basicAuthUserConfig;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    byte[] decodedBytes = Base64.getDecoder().decode(basicAuthUserConfig.getDrowssap());
    auth.inMemoryAuthentication()
        .withUser(basicAuthUserConfig.getUsername())
        .password(passwordEncoder.encode(new String(decodedBytes)))
        .authorities("ROLE_USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // every request need basic auth
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // basic auth for the following endpoints
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/**/order/update")
        .hasRole("USER")
        .antMatchers("/api/**/order/delete")
        .hasRole("USER")
        .antMatchers("/api/**/order/insert")
        .hasRole("USER")
        .anyRequest()
        .permitAll()
        .and()
        .httpBasic();
  }
}
