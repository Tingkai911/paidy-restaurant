package com.paidy.restaurant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.paidy.restaurant.exception.MenuServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mybatis.spring.SqlSessionTemplate;

public class MenuServiceTest {
  private MenuService menuService;
  private SqlSessionTemplate sqlSessionTemplate;
  private final int pageLimit = 10;

  @BeforeEach
  public void init() {
    sqlSessionTemplate = mock(SqlSessionTemplate.class);
    menuService = new MenuService(sqlSessionTemplate, pageLimit);
  }

  @Test
  public void testGetAllMenuItemsSuccess() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    menuService.getAllMenuItems(1);
    assertEquals("getAllMenuItems", stringArgumentCaptor.getValue());
    Map<String, Object> expected = new HashMap<>();
    expected.put("limit", pageLimit);
    expected.put("offset", 0);
    Map<String, Object> actual = mapArgumentCaptor.getValue();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetAllMenuItemsFail() {
    when(sqlSessionTemplate.selectList(anyString(), any())).thenThrow(MenuServiceException.class);
    assertThrows(MenuServiceException.class, () -> menuService.getAllMenuItems(1));
  }

  @Test
  public void testSearchMenuItemsByNameSuccess() {
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
    when(sqlSessionTemplate.selectList(stringArgumentCaptor.capture(), mapArgumentCaptor.capture()))
        .thenReturn(new ArrayList<>());
    menuService.searchMenuItemsByName(1, "ramen");
    assertEquals("searchMenuItemsByName", stringArgumentCaptor.getValue());
    Map<String, Object> expected = new HashMap<>();
    expected.put("limit", pageLimit);
    expected.put("offset", 0);
    expected.put("name", "%ramen%");
    Map<String, Object> actual = mapArgumentCaptor.getValue();
    assertEquals(expected, actual);
  }

  @Test
  public void testSearchMenuItemsByNameFail() {
    when(sqlSessionTemplate.selectList(anyString(), any())).thenThrow(MenuServiceException.class);
    assertThrows(MenuServiceException.class, () -> menuService.searchMenuItemsByName(1, "ramen"));
  }
}
