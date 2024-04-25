package com.assessment.booklibrary.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private String message;

    public ErrorMessage(String message) {
        super();
        this.message = message;
    }
}

