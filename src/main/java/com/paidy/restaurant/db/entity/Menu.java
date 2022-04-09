package com.paidy.restaurant.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends AbstractMyBatisEntity {
  private String name;
  private String description;
  private int duration;
  private float price;
}
