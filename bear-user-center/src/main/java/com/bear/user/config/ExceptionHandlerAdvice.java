package com.bear.user.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:58.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequestException(IllegalArgumentException exception) {
        Map<String, Object> data = new HashMap<>(8);
        data.put("code", HttpStatus.BAD_REQUEST.value());
        data.put("message", exception.getMessage());

        return data;
    }
}