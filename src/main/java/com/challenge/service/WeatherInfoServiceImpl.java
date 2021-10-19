package com.challenge.service;

import com.challenge.entity.WeatherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {

    private final WebClient.Builder builder;

    private final String baseUrl;

    private final String apiKey;

    public WeatherInfoServiceImpl(@Value("${openweathermap.baseUrl}") String baseUrl, @Value("${openweathermap.apiKey}") String apiKey, WebClient.Builder builder) {

        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.builder = builder;
    }

    @Override
    public WeatherInfo getWeatherInfo(String city, String country) throws WeatherInfoNotAvailiableException {

        try {
            WeatherInfo newWeatherInfo = builder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(baseUrl)
                            .queryParam("q", city + "," + country)
                            .queryParam("appid", apiKey)
                            .build())
                    .retrieve().bodyToMono(WeatherInfo.class).block();

            return newWeatherInfo;

        } catch (Exception e) {
            throw new WeatherInfoNotAvailiableException("Weather info not available", e);
        }
    }
}
