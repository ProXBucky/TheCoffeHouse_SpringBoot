package com.hungnv.TheCoffeeHouse.exception;
import com.hungnv.TheCoffeeHouse.exception.common.NotFoundException;


public class EmptyListException extends NotFoundException {
    public EmptyListException(String message) {
        super(message);
    }
}
