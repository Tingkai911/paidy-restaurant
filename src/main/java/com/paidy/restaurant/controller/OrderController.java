package com.paidy.restaurant.controller;

import com.paidy.restaurant.db.entity.Order;
import com.paidy.restaurant.response.Response;
import com.paidy.restaurant.service.OrderService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class OrderController {
  private OrderService orderService;

  @PutMapping("/v1.0/order/update")
  public Response updateOrder(@RequestBody @Valid Order order) {
    Response response = new Response();
    orderService.updateOrder(order);
    response.setMsg("Order updated");
    response.setCode(HttpStatus.OK.value());
    return response;
  }

  @DeleteMapping("/v1.0/order/delete")
  public Response deleteOrder(@RequestParam int id) {
    Response response = new Response();
    orderService.deleteOrder(id);
    response.setMsg("Order deleted");
    response.setCode(HttpStatus.OK.value());
    return response;
  }

  @PostMapping("/v1.0/order/insert")
  public Response insertOrder(@RequestBody @Valid List<Order> order) {
    Response response = new Response();
    orderService.insertOrders(order);
    response.setMsg("New orders created");
    response.setCode(HttpStatus.OK.value());
    return response;
  }

  @GetMapping("/v1.0/order/status")
  public Response<List<Order>> selectOrders(
      @RequestParam int page,
      @RequestParam int tableNumber,
      @RequestParam(required = false) boolean served,
      @RequestParam(required = false) Integer menuId) {
    Response<List<Order>> response = new Response<>();
    if (menuId == null) {
      response.setData(orderService.getOrdersByTableNumber(tableNumber, page, served));
    } else {
      response.setData(
          orderService.getOrdersByTableNumberAndMenuId(tableNumber, page, served, menuId));
    }
    response.setCode(HttpStatus.OK.value());
    return response;
  }
}
