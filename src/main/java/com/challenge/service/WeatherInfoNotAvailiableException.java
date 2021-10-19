package com.challenge.service;

public class WeatherInfoNotAvailiableException extends Exception {

    public WeatherInfoNotAvailiableException(String message) {
        super(message);
    }

    public WeatherInfoNotAvailiableException(String message, Exception e) {
        super(message, e);
    }

}
