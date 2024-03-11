package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintValidationException(MethodArgumentNotValidException e) {
        final List<Error> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Error(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        if (!(errors.isEmpty())) {
            log.warn(errors.toString());
            return new ErrorResponse(errors);
        }
        final List<Error> errorsOther = e.getAllErrors().stream()
                .map(error -> new Error(error.getCode(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.warn(errorsOther.toString());
        return new ErrorResponse(errorsOther);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        Error error = new Error("error message",
                String.format("Параметр '%s' значение '%s' не может быть конвертировано в тип '%s'",
                        e.getName(), e.getValue(), e.getRequiredType().getSimpleName()));
        log.warn(error.getMessage());
        return new ErrorResponse(List.of(error));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintViolationException(ConstraintViolationException e) {
        final List<Error> errors = e.getConstraintViolations().stream()
                .map(error -> new Error(error.getPropertyPath().toString(), error.getMessage()))
                .collect(Collectors.toList());
        log.warn(errors.toString());
        return new ErrorResponse(errors);
    }
}
