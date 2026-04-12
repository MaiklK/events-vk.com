package com.eventsvk.exception;

public class AllowedCityNotFound extends RuntimeException {
    public AllowedCityNotFound(String message) {
        super(message);
    }
}
