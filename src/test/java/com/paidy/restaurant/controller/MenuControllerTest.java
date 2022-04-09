package com.paidy.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.paidy.restaurant.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
public class MenuControllerTest {
  private MockMvc mvc;
  private MenuController menuController;
  private MenuService menuService;

  @BeforeEach
  public void init() {
    menuService = mock(MenuService.class);
    menuController = new MenuController(menuService);
    mvc =
        MockMvcBuilders.standaloneSetup(menuController)
            .setControllerAdvice(new ErrorControllerAdvice())
            .build();
  }

  @Test
  public void testGetAllMenuItemsSuccess() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/items?page=1")).andReturn().getResponse();
    verify(menuService, times(1)).getAllMenuItems(1);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testGetAllMenuItemsFail() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/items")).andReturn().getResponse();
    verify(menuService, times(0)).getAllMenuItems(1);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testSearchMenuItemsByNameSuccess1() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/search?page=1")).andReturn().getResponse();
    verify(menuService, times(1)).getAllMenuItems(1);
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testSearchMenuItemsByNameSuccess2() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/search?page=1&name=ramen")).andReturn().getResponse();
    verify(menuService, times(1)).searchMenuItemsByName(1, "ramen");
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testSearchMenuItemsByNameFail1() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/search")).andReturn().getResponse();
    verify(menuService, times(0)).getAllMenuItems(1);
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void testSearchMenuItemsByNameFail2() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get("/api/v1.0/menu/search?name=ramen")).andReturn().getResponse();
    verify(menuService, times(0)).searchMenuItemsByName(1, "ramen");
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }
}
