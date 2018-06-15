package com.andrew.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FollowUserException extends RuntimeException {

    public FollowUserException(String message) {
        super(message);
    }

}