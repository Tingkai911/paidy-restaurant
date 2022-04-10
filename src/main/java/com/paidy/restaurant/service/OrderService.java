package com.paidy.restaurant.service;

import static com.paidy.restaurant.constant.StatusConstant.*;

import com.paidy.restaurant.db.entity.Order;
import com.paidy.restaurant.exception.OrderServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {
  private final SqlSessionTemplate sqlSessionTemplate;
  private final int pageLimit;
  private final int min;
  private final int max;

  public OrderService(
      SqlSessionTemplate sqlSessionTemplate,
      @Value("${page.limit}") Integer pageLimit,
      @Value("${random-range.min}") Integer min,
      @Value("${random-range.max}") Integer max) {
    this.sqlSessionTemplate = sqlSessionTemplate;
    this.pageLimit = pageLimit;
    this.min = min;
    this.max = max;
  }

  @Transactional
  public void updateOrder(Order order) {
    try {
      sqlSessionTemplate.update("updateOrder", order);
      log.info("updateOrder");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new OrderServiceException(e.getMessage());
    }
  }

  @Transactional
  public void deleteOrder(int id) {
    try {
      sqlSessionTemplate.delete("deleteOrder", id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new OrderServiceException(e.getMessage());
    }
  }

  @Transactional
  public void insertOrders(List<Order> orderList) {
    try {
      // if expected deliver time is not set, randomly assign a duration between 5 to 15 mins
      orderList.forEach(
          order -> {
            if (order.getExpectedDeliverTime() == null) {
              order.setExpectedDeliverTime(
                  order.getCreatedTime().plusMinutes(getRandomNumberInRange()));
            }
          });
      Map<String, Object> params = new HashMap<>();
      params.put("orderList", orderList);
      sqlSessionTemplate.insert("insertOrders", params);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new OrderServiceException(e.getMessage());
    }
  }

  private int getRandomNumberInRange() {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }

  public List<Order> getOrdersByTableNumber(int tableNumber, int pageNo, boolean served) {
    try {
      int offset = (pageLimit * pageNo) - pageLimit;
      Map<String, Object> param = new HashMap<>();
      param.put("limit", pageLimit);
      param.put("offset", offset);
      param.put("tableNumber", tableNumber);
      param.put("status", "%" + SERVED + "%");
      if (served) {
        return sqlSessionTemplate.selectList("selectServedOrdersByTableNumber", param);
      } else {
        return sqlSessionTemplate.selectList("selectNotServedOrdersByTableNumber", param);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new OrderServiceException(e.getMessage());
    }
  }

  public List<Order> getOrdersByTableNumberAndMenuId(
      int tableNumber, int pageNo, boolean served, int menuId) {
    try {
      int offset = (pageLimit * pageNo) - pageLimit;
      Map<String, Object> param = new HashMap<>();
      param.put("limit", pageLimit);
      param.put("offset", offset);
      param.put("tableNumber", tableNumber);
      param.put("menuId", menuId);
      param.put("status", "%" + SERVED + "%");
      if (served) {
        return sqlSessionTemplate.selectList("selectServedOrdersByMenuIdAndTableNumber", param);
      } else {
        return sqlSessionTemplate.selectList("selectNotServedOrdersByMenuIdAndTableNumber", param);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new OrderServiceException(e.getMessage());
    }
  }
}
