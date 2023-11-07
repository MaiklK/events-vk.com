package com.eventsvk.util.exceptions;

public class UserCounterNotFoundException extends RuntimeException {
    public UserCounterNotFoundException(String message) {
        super(message);
    }
}
