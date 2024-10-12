package com.hungnv.TheCoffeeHouse.exception;
import com.hungnv.TheCoffeeHouse.exception.common.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
