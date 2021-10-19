package com.challenge.service;

import com.challenge.entity.WeatherInfo;

public interface WeatherInfoService {

    WeatherInfo getWeatherInfo(String city, String country) throws WeatherInfoNotAvailiableException;

}
