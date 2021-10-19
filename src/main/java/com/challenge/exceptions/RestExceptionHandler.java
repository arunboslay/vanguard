package com.challenge.exceptions;

import com.challenge.model.Message;
import com.challenge.service.WeatherInfoNotAvailiableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGlobalException(
            Exception ex) {
        log.error("Error occurred while processing request", ex);
        return new ResponseEntity<>(Message.builder().message("Internal error occurred, contact administrator").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WeatherInfoNotAvailiableException.class)
    protected ResponseEntity<Message> handleWeatherInfoNotAvailableException(
            Exception ex) {
        log.error("Weather information not available at this time", ex);
        return new ResponseEntity<>(Message.builder().message("Weather information not available at this time").build(), HttpStatus.NOT_FOUND);
    }
}
