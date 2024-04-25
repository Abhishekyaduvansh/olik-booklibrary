package com.assessment.booklibrary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class BookServiceException extends Exception {
    private String message;
    private HttpStatusCode statusCode;

    public BookServiceException(String errorMessage) {
        super(errorMessage);
    }

    public BookServiceException(String errorMessage, HttpStatusCode statusCode) {
        super(errorMessage);
        this.message = errorMessage;
        this.statusCode = statusCode;
    }
}
