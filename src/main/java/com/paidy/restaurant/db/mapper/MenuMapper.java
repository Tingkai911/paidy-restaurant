package com.paidy.restaurant.db.mapper;

import com.paidy.restaurant.db.entity.Menu;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MenuMapper {
  @Select("SELECT * FROM paidy_restaurant.menu WHERE available=1 LIMIT #{offset}, #{limit}")
  List<Menu> getAllMenuItems(@Param("offset") int offset, @Param("limit") int limit);

  @Select(
      "SELECT * FROM paidy_restaurant.menu WHERE LOWER(name) LIKE #{name} AND available=1 LIMIT #{offset}, #{limit}")
  List<Menu> searchMenuItemsByName(
      @Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);
}
