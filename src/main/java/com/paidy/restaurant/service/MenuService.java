package com.paidy.restaurant.service;

import com.paidy.restaurant.db.entity.Menu;
import com.paidy.restaurant.exception.MenuServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuService {
  private final SqlSessionTemplate sqlSessionTemplate;
  private final int pageLimit;

  public MenuService(SqlSessionTemplate sqlSessionTemplate, @Value("${page.limit}") int pageLimit) {
    this.sqlSessionTemplate = sqlSessionTemplate;
    this.pageLimit = pageLimit;
  }

  public List<Menu> getAllMenuItems(int pageNo) throws MenuServiceException {
    try {
      int offset = (pageLimit * pageNo) - pageLimit;
      Map<String, Object> param = new HashMap<>();
      param.put("limit", pageLimit);
      param.put("offset", offset);
      return sqlSessionTemplate.selectList("getAllMenuItems", param);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new MenuServiceException(e.getMessage());
    }
  }

  public List<Menu> searchMenuItemsByName(int pageNo, String name) {
    try {
      int offset = (pageLimit * pageNo) - pageLimit;
      Map<String, Object> param = new HashMap<>();
      param.put("limit", pageLimit);
      param.put("offset", offset);
      param.put("name", "%" + name.toLowerCase() + "%");
      return sqlSessionTemplate.selectList("searchMenuItemsByName", param);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new MenuServiceException(e.getMessage());
    }
  }
}
