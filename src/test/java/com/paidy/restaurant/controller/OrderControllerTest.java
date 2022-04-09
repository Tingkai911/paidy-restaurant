package com.paidy.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paidy.restaurant.db.entity.Order;
import com.paidy.restaurant.service.OrderService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
public class OrderControllerTest {
  private MockMvc mvc;
  private OrderController orderController;
  private OrderService orderService;

  @BeforeEach
  public void init() {
    orderService = mock(OrderService.class);
    orderController = new OrderController(orderService);
    mvc =
        MockMvcBuilders.standaloneSetup(orderController)
            .setControllerAdvice(new ErrorControllerAdvice())
            .build();
  }

  @Test
  public void testDeleteOrderSuccess() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(delete("/api/v1.0/order/delete?id=1")).andReturn().getResponse();
    verify(orderService, times(1)).deleteOrder(1);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testDeleteOrderFail() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(delete("/api/v1.0/order/delete")).andReturn().getResponse();
    verify(orderService, times(0)).deleteOrder(1);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testSelectOrdersSuccess1() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/order/status?page=1&tableNumber=2")).andReturn().getResponse();
    verify(orderService, times(1)).getOrdersByTableNumber(2, 1, false);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testSelectOrdersSuccess2() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/order/status?page=1&tableNumber=2&served=true"))
            .andReturn()
            .getResponse();
    verify(orderService, times(1)).getOrdersByTableNumber(2, 1, true);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testSelectOrdersSuccess3() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/order/status?page=1&tableNumber=2&served=true&menuId=10"))
            .andReturn()
            .getResponse();
    verify(orderService, times(1)).getOrdersByTableNumberAndMenuId(2, 1, true, 10);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testSelectOrdersFail() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/order/status?tableNumber=2")).andReturn().getResponse();
    verify(orderService, times(0)).getOrdersByTableNumber(2, 1, false);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testUpdateOrderSuccess() throws Exception {
    Order order = new Order();
    order.setStatus("served");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setCreatedTime(LocalDateTime.parse("2022-01-01 00:00:00", dateTimeFormatter));
    order.setExpectedDeliverTime(LocalDateTime.parse("2022-01-01 01:00:00", dateTimeFormatter));
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(order);

    MockHttpServletResponse response =
        mvc.perform(
                put("/api/v1.0/order/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(1)).updateOrder(order);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testUpdateOrderSuccess2() throws Exception {
    Order order = new Order();
    order.setStatus("served");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setCreatedTime(LocalDateTime.parse("2022-01-01 00:00:00", dateTimeFormatter));
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(order);

    MockHttpServletResponse response =
        mvc.perform(
                put("/api/v1.0/order/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(1)).updateOrder(order);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testUpdateOrderFail1() throws Exception {
    Order order = new Order();
    order.setStatus("served");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setCreatedTime(LocalDateTime.parse("2022-01-01 02:00:00", dateTimeFormatter));
    order.setExpectedDeliverTime(LocalDateTime.parse("2022-01-01 01:00:00", dateTimeFormatter));
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(order);

    MockHttpServletResponse response =
        mvc.perform(
                put("/api/v1.0/order/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(0)).updateOrder(order);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testUpdateOrderFail2() throws Exception {
    Order order = new Order();
    order.setStatus("invalid");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setCreatedTime(LocalDateTime.parse("2022-01-01 02:00:00", dateTimeFormatter));
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(order);

    MockHttpServletResponse response =
        mvc.perform(
                put("/api/v1.0/order/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(0)).updateOrder(order);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testUpdateOrderFail3() throws Exception {
    Order order = new Order();
    order.setStatus("served");
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(order);

    MockHttpServletResponse response =
        mvc.perform(
                put("/api/v1.0/order/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(0)).updateOrder(order);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testOrderInsertSuccess() throws Exception {
    Order order = new Order();
    order.setStatus("ordered");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    order.setCreatedTime(LocalDateTime.parse("2022-01-01 00:00:00", dateTimeFormatter));
    order.setExpectedDeliverTime(LocalDateTime.parse("2022-01-01 01:00:00", dateTimeFormatter));
    order.setMenuId(1L);
    order.setStaffId(2L);
    order.setTableNumber(3);
    List<Order> orderList = Arrays.asList(order, order);

    String content = new ObjectMapper().findAndRegisterModules().writeValueAsString(orderList);

    MockHttpServletResponse response =
        mvc.perform(
                post("/api/v1.0/order/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
            .andReturn()
            .getResponse();
    verify(orderService, times(1)).insertOrders(orderList);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }
}
