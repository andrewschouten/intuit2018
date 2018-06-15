package com.andrew.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(String message) {
        super(message);
    }

}