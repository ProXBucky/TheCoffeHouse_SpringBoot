package com.hungnv.TheCoffeeHouse.exception;


import com.hungnv.TheCoffeeHouse.exception.common.InternalServerException;

public class ErrorImageException extends InternalServerException {
    public ErrorImageException(String message) {
        super(message);
    }
}
