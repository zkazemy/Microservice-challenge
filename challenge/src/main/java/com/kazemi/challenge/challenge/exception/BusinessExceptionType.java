package com.kazemi.challenge.challenge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The {@code MovieRate} represents the business exceptions types
 *
 * @author Zahra Kazemi
 */
@Getter
public enum BusinessExceptionType {

    GENERAL(1000, HttpStatus.INTERNAL_SERVER_ERROR, "com.kazemi.challenge.error.general"),
    INVALID_DATA(1001, HttpStatus.BAD_REQUEST, "com.kazemi.challenge.error.bad-data"),
    NOT_FOUND(1002, HttpStatus.NOT_FOUND, "com.kazemi.challenge.error.not_found"),
    DUPLICATE_VOTE(1003, HttpStatus.CONFLICT, "com.kazemi.challenge.error.duplicate_vote");

    private final int code;
    private final HttpStatus httpStatus;
    private final String messageKey;

    BusinessExceptionType(int code, HttpStatus httpStatus, String messageKey) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }
}
