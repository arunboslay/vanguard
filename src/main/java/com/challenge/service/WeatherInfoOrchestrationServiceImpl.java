package com.challenge.service;

import com.challenge.entity.Weather;
import com.challenge.entity.WeatherInfo;
import com.challenge.model.Message;
import com.challenge.repository.WeatherInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherInfoOrchestrationServiceImpl implements WeatherInfoOrchestrationService {

    private final WeatherInfoService weatherInfoService;
    private final WeatherInfoRepository weatherInfoRepository;

    public WeatherInfoOrchestrationServiceImpl(WeatherInfoService weatherInfoService, WeatherInfoRepository weatherInfoRepository) {
        this.weatherInfoService = weatherInfoService;
        this.weatherInfoRepository = weatherInfoRepository;
    }

    @Override
    public Message updateAndReturnWeatherDescription(String city, String country) throws WeatherInfoNotAvailiableException {

        WeatherInfo newWeatherInfo = weatherInfoService.getWeatherInfo(city, country);

        if (newWeatherInfo == null || newWeatherInfo.getWeather() == null || newWeatherInfo.getWeather().size() == 0) {
            throw new WeatherInfoNotAvailiableException("Weather info not available");
        }

        WeatherInfo currentWeatherInfo = weatherInfoRepository.findWeatherInfoByCityAndCountry(city, country);
        if (currentWeatherInfo != null) {
            BeanUtils.copyProperties(newWeatherInfo, currentWeatherInfo, "weatherInfoId", "cityCode", "countryCode");
            weatherInfoRepository.save(currentWeatherInfo);
        } else {
            newWeatherInfo.setCityCode(city);
            newWeatherInfo.setCountryCode(country);
            weatherInfoRepository.save(newWeatherInfo);
        }

        WeatherInfo weatherInfoByCityAndCountry = weatherInfoRepository.findWeatherInfoByCityAndCountry(city, country);

        List<Weather> weather = weatherInfoByCityAndCountry.getWeather();
        String message = weather.stream()
                .map(w -> w.getDescription())
                .collect(Collectors.joining(","));
        return Message.builder().message(message).build();
    }

}
