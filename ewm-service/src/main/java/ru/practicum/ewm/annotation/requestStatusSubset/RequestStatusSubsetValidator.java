package ru.practicum.ewm.annotation.requestStatusSubset;

import ru.practicum.ewm.dto.request.RequestStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RequestStatusSubsetValidator implements ConstraintValidator<RequestStatusSubset, RequestStatus> {
    private RequestStatus[] subset;

    @Override
    public void initialize(RequestStatusSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(RequestStatus value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}