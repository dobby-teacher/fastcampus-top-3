package com.fastcampus.nextgraphql.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
