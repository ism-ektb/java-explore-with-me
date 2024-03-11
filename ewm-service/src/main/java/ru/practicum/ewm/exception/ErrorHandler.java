package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewm.exception.exception.BaseRelationshipException;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintValidation(ConstraintViolationException e) {
        final List<Error> errors = e.getConstraintViolations().stream()
                .map(error -> new Error(error.getPropertyPath().toString(), error.getMessage()))
                .collect(Collectors.toList());
        log.warn(errors.toString());
        return new ErrorResponse(errors);
    }

    @ExceptionHandler(NoFoundObjectException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The required object was not found.")
    public ErrorResponse onNoFoundObjectException(NoFoundObjectException e) {
        log.warn("The required object was not found. {}", e.getMessage());
        return new ErrorResponse(List.of(new Error("The required object was not found.", e.getMessage())));
    }

    @ExceptionHandler(BaseRelationshipException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "The required object was not found.")
    public ErrorResponse onBaseRelationshipException(BaseRelationshipException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(List.of(new Error("The required object was not found.", e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onMethodArgumentNotValid(MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(List.of(new Error("Incorrectly made request.", e.getMessage())));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(List.of(new Error("Incorrectly made request.", e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrectly made request.")
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

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrectly made request.")
    public ErrorResponse onIllegalArgumentException(final ConversionFailedException e) {
        log.warn("Unknown state: {} ", e.getMessage());
        return new ErrorResponse(List.of(new Error("Incorrectly " +
                "made request.", "Unknown state: " + e.getValue())));
    }

}
