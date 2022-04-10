package com.paidy.restaurant.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "basic-auth")
public class BasicAuthUserConfig {
  private String username;
  private String drowssap;
}
