package ru.shift.userimporter.api.error;

import lombok.Getter;

@Getter
public enum ErrorType {
    FILE_NOT_FOUND(404),
    FILE_ALREADY_EXISTS(409),
    INVALID_FILE(400),
    VALIDATION_ERROR(400),
    INTERNAL_ERROR(500);

    private final int httpStatus;

    ErrorType(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}