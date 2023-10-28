package com.eventsvk.util.exceptions;

public class AccessTokenNotFoundException extends RuntimeException{
    public AccessTokenNotFoundException(String message) {
        super(message);
    }
}
