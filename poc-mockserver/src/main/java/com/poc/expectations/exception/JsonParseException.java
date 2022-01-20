package com.poc.expectations.exception;

public class JsonParseException extends RuntimeException {

    public JsonParseException (String message, Throwable t) {
        super(message, t);
    }
}
