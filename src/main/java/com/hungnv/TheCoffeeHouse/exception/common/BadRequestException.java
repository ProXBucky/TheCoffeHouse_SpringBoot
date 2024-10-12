package com.hungnv.TheCoffeeHouse.exception.common;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
