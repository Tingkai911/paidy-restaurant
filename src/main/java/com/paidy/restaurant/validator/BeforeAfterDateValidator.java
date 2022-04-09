package com.paidy.restaurant.validator;

import com.paidy.restaurant.annotation.BeforeAfterDate;
import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

@Slf4j
public class BeforeAfterDateValidator implements ConstraintValidator<BeforeAfterDate, Object> {
  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize(BeforeAfterDate beforeAfterDate) {
    firstFieldName = beforeAfterDate.first();
    secondFieldName = beforeAfterDate.second();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
    BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
    LocalDateTime firstTime = (LocalDateTime) wrapper.getPropertyValue(firstFieldName);
    LocalDateTime secondTime = (LocalDateTime) wrapper.getPropertyValue(secondFieldName);
    if (firstTime == null) {
      log.error(firstFieldName + ":" + firstTime + " " + secondFieldName + ":" + secondTime);
      return false;
    }
    if (secondTime != null) {
      if (firstTime.isAfter(secondTime)) {
        log.error(firstFieldName + ":" + firstTime + " " + secondFieldName + ":" + secondTime);
        return false;
      }
    }
    return true;
  }
}
