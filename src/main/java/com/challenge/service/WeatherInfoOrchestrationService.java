package com.challenge.service;

import com.challenge.model.Message;

public interface WeatherInfoOrchestrationService {

    Message updateAndReturnWeatherDescription(String city, String country) throws WeatherInfoNotAvailiableException;

}
