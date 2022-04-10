package com.paidy.restaurant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.paidy.restaurant.db.entity.Order;
import com.paidy.restaurant.exception.OrderServiceException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mybatis.spring.SqlSessionTemplate;

public class OrderServiceTest {
  private OrderService orderService;
  private SqlSessionTemplate sqlSessionTemplate;
  private final int pageLimit = 10;

  @BeforeEach
  public void init() {
    sqlSessionTemplate = mock(SqlSessionTemplate.class);
    orderService = new OrderService(sqlSessionTemplate, pageLimit, 5, 10);
  }

  @Test
  public void testUpdateOrderSuccess() {
    Order order = new Order();
    order.setStaffId(100L);
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
    when(sqlSessionTemplate.update(stringArgumentCaptor.capture(), orderArgumentCaptor.capture()))
        .thenReturn(1);
    orderService.updateOrder(order);
    assertEquals("updateOrder", stringArgumentCaptor.getValue());
    assertEquals(order, orderArgumentCaptor.getValue());
  }

  @Test
  public void testUpdateOrderFail() {
    Order order = new Order();
    order.setStaffId(100L);
    when(sqlSessionTemplate.update(anyString(), any())).thenThrow(OrderServiceException.class);
    assertThrows(OrderServiceException.class, () -> orderService.updateOrder(order));
  }

  @Test
  public void testDeleteOrderSuccess() {
    when(sqlSessionTemplate.update("deleteOrder", 1)).thenReturn(1);
    orderService.deleteOrder(1);
  }

  @Test
  public void testDeleteOrderFail() {
    when(sqlSessionTemplate.delete(anyString(), any())).thenThrow(OrderServiceException.class);
    assertThrows(OrderServiceException.class, () -> orderService.deleteOrder(1));
  }

  @Test
  public void testInsertOrderSuccess() {
    Order order = new Order();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setExpectedDeliverTime(LocalDateTime.parse("2022-01-01 02:00:00", dateTimeFormatter));
    order.setStaffId(100L);
    List<Order> orderList = Arrays.asList(order);
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.insert(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(1);
    orderService.insertOrders(orderList);
    assertEquals("insertOrders", stringArgumentCaptor.getValue());
    Map<String, Object> params = new HashMap<>();
    params.put("orderList", orderList);
    assertEquals(params, mapArgumentCaptor.getValue());
  }

  @Test
  public void testInsertOrderFailed() {
    Order order = new Order();
    order.setStaffId(100L);
    List<Order> orderList = Arrays.asList(order);
    when(sqlSessionTemplate.insert(anyString(), any())).thenThrow(OrderServiceException.class);
    assertThrows(OrderServiceException.class, () -> orderService.insertOrders(orderList));
  }

  @Test
  public void testGetOrdersByTableNumberSuccess1() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    orderService.getOrdersByTableNumber(1, 1, true);
    assertEquals("selectServedOrdersByTableNumber", stringArgumentCaptor.getValue());
    Map<String, Object> param = new HashMap<>();
    param.put("limit", pageLimit);
    param.put("offset", 0);
    param.put("tableNumber", 1);
    param.put("status", "%served%");
    assertEquals(param, mapArgumentCaptor.getValue());
  }

  @Test
  public void testGetOrdersByTableNumberSuccess2() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    orderService.getOrdersByTableNumber(1, 1, false);
    assertEquals("selectNotServedOrdersByTableNumber", stringArgumentCaptor.getValue());
    Map<String, Object> param = new HashMap<>();
    param.put("limit", pageLimit);
    param.put("offset", 0);
    param.put("tableNumber", 1);
    param.put("status", "%served%");
    assertEquals(param, mapArgumentCaptor.getValue());
  }

  @Test
  public void testGetOrdersByTableNumberFail() {
    when(sqlSessionTemplate.selectList(anyString(), any())).thenThrow(OrderServiceException.class);
    assertThrows(
        OrderServiceException.class, () -> orderService.getOrdersByTableNumber(1, 1, true));
  }

  @Test
  public void testGetOrdersByTableNumberAndMenuIdSuccess1() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    orderService.getOrdersByTableNumberAndMenuId(1, 1, true, 2);
    assertEquals("selectServedOrdersByMenuIdAndTableNumber", stringArgumentCaptor.getValue());
    Map<String, Object> param = new HashMap<>();
    param.put("limit", pageLimit);
    param.put("offset", 0);
    param.put("menuId", 2);
    param.put("tableNumber", 1);
    param.put("status", "%served%");
    assertEquals(param, mapArgumentCaptor.getValue());
  }

  @Test
  public void testGetOrdersByTableNumberAndMenuIdSuccess2() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    orderService.getOrdersByTableNumberAndMenuId(1, 1, false, 2);
    assertEquals("selectNotServedOrdersByMenuIdAndTableNumber", stringArgumentCaptor.getValue());
    Map<String, Object> param = new HashMap<>();
    param.put("limit", pageLimit);
    param.put("offset", 0);
    param.put("menuId", 2);
    param.put("tableNumber", 1);
    param.put("status", "%served%");
    assertEquals(param, mapArgumentCaptor.getValue());
  }

  @Test
  public void testGetOrdersByTableNumberAndMenuIdFail() {
    when(sqlSessionTemplate.selectList(anyString(), any())).thenThrow(OrderServiceException.class);
    assertThrows(
        OrderServiceException.class,
        () -> orderService.getOrdersByTableNumberAndMenuId(1, 1, true, 2));
  }
}
