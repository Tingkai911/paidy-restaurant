package com.paidy.restaurant.controller;

import com.paidy.restaurant.db.entity.Menu;
import com.paidy.restaurant.response.Response;
import com.paidy.restaurant.service.MenuService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class MenuController {
  private MenuService menuService;

  @GetMapping("/v1.0/menu/items")
  public Response<List<Menu>> getAllMenuItems(@RequestParam int page) {
    Response<List<Menu>> response = new Response<>();
    response.setData(menuService.getAllMenuItems(page));
    response.setCode(HttpStatus.OK.value());
    return response;
  }

  @GetMapping("/v1.0/menu/search")
  public Response<List<Menu>> searchMenuItemsByName(
      @RequestParam int page, @RequestParam(required = false) String name) {
    Response<List<Menu>> response = new Response<>();
    if (name == null || name.trim().isEmpty()) {
      response.setData(menuService.getAllMenuItems(page));
      response.setMsg("returns all menu items as \"name\" param is empty");
    } else {
      response.setData(menuService.searchMenuItemsByName(page, name));
    }
    response.setCode(HttpStatus.OK.value());
    return response;
  }
}
