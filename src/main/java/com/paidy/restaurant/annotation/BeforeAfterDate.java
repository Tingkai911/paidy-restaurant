package com.paidy.restaurant.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.paidy.restaurant.validator.BeforeAfterDateValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BeforeAfterDateValidator.class)
@Documented
public @interface BeforeAfterDate {
  String first();

  String second();

  String message() default "Start time must be before end time";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
