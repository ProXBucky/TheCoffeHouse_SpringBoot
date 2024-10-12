package com.hungnv.TheCoffeeHouse.exception;

import com.hungnv.TheCoffeeHouse.exception.common.NotFoundException;

public class StoreNotFoundException extends NotFoundException {
    public StoreNotFoundException(String message) {
        super(message);
    }
}
