package com.satellite.messenger.config;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFoundException(final NotFoundException notFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        log.error("Incoming illegalArgumentException, mapping to '{}'", httpStatus, notFoundException);
        return new ResponseEntity<>(httpStatus);
    }

}