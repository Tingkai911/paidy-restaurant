package com.paidy.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.paidy.restaurant.exception.MenuServiceException;
import com.paidy.restaurant.exception.OrderServiceException;
import com.paidy.restaurant.response.Response;
import java.util.HashSet;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MissingServletRequestParameterException;

public class ErrorControllerTest {
  private ErrorControllerAdvice errorControllerAdvice;

  @BeforeEach
  public void init() {
    errorControllerAdvice = new ErrorControllerAdvice();
  }

  @Test
  public void testHandleRequestError1() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("cause", new HashSet<>());
    Response response = errorControllerAdvice.handleRequestError(constraintViolationException);
    assertEquals(400, response.getCode());
    assertEquals("cause", response.getMsg());
  }

  @Test
  public void testHandleRequestError2() {
    MissingServletRequestParameterException constraintViolationException =
        new MissingServletRequestParameterException("cause", "causetype");
    Response response = errorControllerAdvice.handleRequestError(constraintViolationException);
    assertEquals(400, response.getCode());
  }

  @Test
  public void testHandleServiceError1() {
    MenuServiceException menuServiceException = new MenuServiceException("cause");
    Response response = errorControllerAdvice.handleServiceError(menuServiceException);
    assertEquals(500, response.getCode());
    assertEquals("cause", response.getMsg());
  }

  @Test
  public void testHandleServiceError2() {
    OrderServiceException orderServiceException = new OrderServiceException("cause");
    Response response = errorControllerAdvice.handleServiceError(orderServiceException);
    assertEquals(500, response.getCode());
    assertEquals("cause", response.getMsg());
  }

  @Test
  public void testHandleServiceError3() {
    Exception exception = new Exception("cause");
    Response response = errorControllerAdvice.handleServiceError(exception);
    assertEquals(500, response.getCode());
    assertEquals("cause", response.getMsg());
  }
}
