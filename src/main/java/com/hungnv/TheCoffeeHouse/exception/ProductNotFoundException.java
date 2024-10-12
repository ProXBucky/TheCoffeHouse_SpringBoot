package com.hungnv.TheCoffeeHouse.exception;

import com.hungnv.TheCoffeeHouse.exception.common.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
