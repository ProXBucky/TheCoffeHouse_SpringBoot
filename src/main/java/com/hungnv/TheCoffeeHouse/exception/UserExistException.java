package com.hungnv.TheCoffeeHouse.exception;

import com.hungnv.TheCoffeeHouse.exception.common.ConflictException;

public class UserExistException extends ConflictException {
    public UserExistException(String message) {
        super(message);
    }
}
