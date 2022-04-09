package com.paidy.restaurant.validator;

import static com.paidy.restaurant.constant.StatusConstant.*;

import com.paidy.restaurant.annotation.OrderStatus;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

@Slf4j
public class OrderStatusValidator implements ConstraintValidator<OrderStatus, Object> {
  private String statusFieldName;

  @Override
  public void initialize(OrderStatus orderStatus) {
    statusFieldName = orderStatus.status();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
    String status = (String) wrapper.getPropertyValue(statusFieldName);

    if (ORDERED.equals(status)
        || PREPARED.equals(status)
        || SERVED.equals(status)
        || CANCELED.equals(status)) {
      return true;
    }

    log.error("status: " + status);
    return false;
  }
}
