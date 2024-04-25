package com.assessment.booklibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseStatus
public class BookServiceExceptionHandler {

    @ExceptionHandler(value = BookServiceException.class)
    public ErrorMessage handleBookServiceException(BookServiceException ex, WebRequest webRequest) {
        return new ErrorMessage(ex.getMessage());
    }
}
