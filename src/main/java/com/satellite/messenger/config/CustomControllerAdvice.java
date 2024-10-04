package com.satellite.messenger.config;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFoundException(final NotFoundException notFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        log.error("Incoming illegalArgumentException, mapping to '{}'", httpStatus, notFoundException);
        return new ResponseEntity<>(httpStatus);
    }

}