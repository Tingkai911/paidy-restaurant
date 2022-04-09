package com.paidy.restaurant.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.paidy.restaurant.validator.OrderStatusValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OrderStatusValidator.class)
@Documented
public @interface OrderStatus {
  String status();

  String message() default "invalid status";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
