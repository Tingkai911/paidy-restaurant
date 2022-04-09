package com.paidy.restaurant.service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.SqlSessionTemplate;

public class OrderServiceTest {
  private OrderService orderService;
  private SqlSessionTemplate sqlSessionTemplate;
  private final int pageLimit = 10;

  @BeforeEach
  public void init() {
    sqlSessionTemplate = mock(SqlSessionTemplate.class);
    orderService = new OrderService(sqlSessionTemplate, pageLimit);
  }
}
