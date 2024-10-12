package com.hungnv.TheCoffeeHouse.exception;

import com.hungnv.TheCoffeeHouse.exception.common.BadRequestException;

public class MissingParameterException extends BadRequestException {
    public MissingParameterException(String message) {
        super(message);
    }
}
