package com.paidy.restaurant.exception;

public class OrderServiceException extends RuntimeException {
  public OrderServiceException(String message) {
    super(message);
  }
}
