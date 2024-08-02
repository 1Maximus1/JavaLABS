package com.di.util;

public class CircularInjectException extends RuntimeException {
    public CircularInjectException(String message) {
        super(message);
    }
}
