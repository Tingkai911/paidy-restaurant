package com.paidy.restaurant.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paidy.restaurant.annotation.BeforeAfterDate;
import com.paidy.restaurant.annotation.OrderStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@EqualsAndHashCode(callSuper = true)
@Data
@BeforeAfterDate(
    first = "createdTime",
    second = "expectedDeliverTime",
    message = "createdTime must not be after expectedDeliverTime and createdTime cannot be empty.")
@OrderStatus(
    status = "status",
    message = "status must be one of the following: ordered, prepared, served, canceled")
public class Order extends AbstractMyBatisEntity {
  @NotNull private Long menuId;
  @NotNull private Long staffId;
  @NotNull private Integer tableNumber;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime expectedDeliverTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime actualDeliverTime;

  @NotNull private String status;
  private String request;
}
