package com.paidy.restaurant.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Staff extends AbstractMyBatisEntity {
  private String name;
}
