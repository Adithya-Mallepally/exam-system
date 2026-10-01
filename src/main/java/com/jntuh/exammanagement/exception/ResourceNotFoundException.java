package com.jntuh.exammanagement.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("Resource not found: %s with %s = %s", resource, field, value));
    }
}
