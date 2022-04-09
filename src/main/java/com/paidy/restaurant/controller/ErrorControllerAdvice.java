package com.paidy.restaurant.controller;

import com.paidy.restaurant.exception.MenuServiceException;
import com.paidy.restaurant.exception.OrderServiceException;
import com.paidy.restaurant.response.Response;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice {
  @ExceptionHandler(
      value = {
        MethodArgumentTypeMismatchException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentNotValidException.class,
        ConstraintViolationException.class
      })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handleRequestError(Exception ex) {
    log.error(ex.getMessage(), ex);
    Response response = new Response();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setMsg(ex.getMessage());
    return response;
  }

  @ExceptionHandler(
      value = {MenuServiceException.class, OrderServiceException.class, Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Response handleServiceError(Exception ex) {
    log.error(ex.getMessage(), ex);
    Response response = new Response();
    response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setMsg(ex.getMessage());
    return response;
  }
}
