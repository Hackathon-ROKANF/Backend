package com.springboot.knockbackend.exception;

public class RealEstateApiException extends RuntimeException {
    public RealEstateApiException(String msg) { super(msg); }
    public RealEstateApiException(String msg, Throwable cause) { super(msg, cause); }
}
