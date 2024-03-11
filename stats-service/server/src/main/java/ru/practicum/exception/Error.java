package ru.practicum.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Error {
    public Error(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    private final String fieldName;
    private final String message;
}
