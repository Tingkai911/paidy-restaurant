package com.paidy.restaurant.db.mapper;

import com.paidy.restaurant.db.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {
  @Insert({
    "<script>",
    "INSERT INTO paidy_restaurant.order",
    "(created_time, expected_deliver_time, status, menu_id, staff_id, request, table_number)",
    "VALUES"
        + "<foreach item='order' collection='orderList' open='' separator=',' close=''>"
        + "("
        + "#{order.createdTime},"
        + "#{order.expectedDeliverTime},"
        + "#{order.status},"
        + "#{order.menuId},"
        + "#{order.staffId},"
        + "#{order.request},"
        + "#{order.tableNumber}"
        + ")"
        + "</foreach>",
    "</script>"
  })
  void insertOrders(@Param("orderList") List<Order> orderList);

  @Select(
      "SELECT * FROM paidy_restaurant.order WHERE table_number=#{tableNumber} AND status LIKE #{status} LIMIT #{offset}, #{limit}")
  @Results(
      value = {
        @Result(property = "menuId", column = "menu_id"),
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "tableNumber", column = "table_number"),
        @Result(property = "createdTime", column = "created_time"),
        @Result(property = "expectedDeliverTime", column = "expected_deliver_time"),
        @Result(property = "actualDeliverTime", column = "actual_deliver_time")
      })
  List<Order> selectServedOrdersByTableNumber(
      @Param("tableNumber") int tableNumber,
      @Param("status") String status,
      @Param("offset") int offset,
      @Param("limit") int limit);

  @Select(
      "SELECT * FROM paidy_restaurant.order WHERE table_number=#{tableNumber} AND status NOT LIKE #{status} LIMIT #{offset}, #{limit}")
  @Results(
      value = {
        @Result(property = "menuId", column = "menu_id"),
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "tableNumber", column = "table_number"),
        @Result(property = "createdTime", column = "created_time"),
        @Result(property = "expectedDeliverTime", column = "expected_deliver_time"),
        @Result(property = "actualDeliverTime", column = "actual_deliver_time")
      })
  List<Order> selectNotServedOrdersByTableNumber(
      @Param("tableNumber") int tableNumber,
      @Param("status") String status,
      @Param("offset") int offset,
      @Param("limit") int limit);

  @Select(
      "SELECT * FROM paidy_restaurant.order WHERE menu_id=#{menuId} AND table_number=#{tableNumber} AND status LIKE #{status} LIMIT #{offset}, #{limit}")
  @Results(
      value = {
        @Result(property = "menuId", column = "menu_id"),
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "tableNumber", column = "table_number"),
        @Result(property = "createdTime", column = "created_time"),
        @Result(property = "expectedDeliverTime", column = "expected_deliver_time"),
        @Result(property = "actualDeliverTime", column = "actual_deliver_time")
      })
  List<Order> selectServedOrdersByMenuIdAndTableNumber(
      @Param("menuId") int menuId,
      @Param("tableNumber") int tableNumber,
      @Param("status") String status,
      @Param("offset") int offset,
      @Param("limit") int limit);

  @Select(
      "SELECT * FROM paidy_restaurant.order WHERE menu_id=#{menuId} AND table_number=#{tableNumber} AND status NOT LIKE #{status} LIMIT #{offset}, #{limit}")
  @Results(
      value = {
        @Result(property = "menuId", column = "menu_id"),
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "tableNumber", column = "table_number"),
        @Result(property = "createdTime", column = "created_time"),
        @Result(property = "expectedDeliverTime", column = "expected_deliver_time"),
        @Result(property = "actualDeliverTime", column = "actual_deliver_time")
      })
  List<Order> selectNotServedOrdersByMenuIdAndTableNumber(
      @Param("menuId") int menuId,
      @Param("tableNumber") int tableNumber,
      @Param("status") String status,
      @Param("offset") int offset,
      @Param("limit") int limit);

  @Delete("DELETE FROM paidy_restaurant.order WHERE id=#{id}")
  void deleteOrder(int id);

  @Update(
      "UPDATE paidy_restaurant.order "
          + "SET menu_id=#{menuId},"
          + "staff_id=#{staffId},"
          + "table_number=#{tableNumber},"
          + "created_time=#{createdTime},"
          + "expected_deliver_time=#{expectedDeliverTime},"
          + "actual_deliver_time=#{actualDeliverTime},"
          + "status=#{status},"
          + "request=#{request}"
          + "WHERE id=#{id}")
  void updateOrder(Order order);
}
