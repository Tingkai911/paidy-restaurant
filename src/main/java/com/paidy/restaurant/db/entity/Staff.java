package com.paidy.restaurant.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Staff extends AbstractMyBatisEntity {
  private String name;
  private String role;
  private String password;
  private String username;
  private boolean enabled;
}
