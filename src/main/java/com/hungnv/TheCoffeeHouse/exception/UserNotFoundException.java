package com.hungnv.TheCoffeeHouse.exception;
import com.hungnv.TheCoffeeHouse.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
