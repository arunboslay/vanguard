package com.challenge.service;

import com.challenge.entity.Weather;
import com.challenge.entity.WeatherInfo;
import com.challenge.repository.WeatherInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class WeatherInfoOrchestrationServiceImplTest {

    public static final String CITY = "London";
    public static final String COUNTRY = "uk";
    @Mock
    private WeatherInfoService weatherInfoService;

    @Mock
    private WeatherInfoRepository weatherInfoRepository;

    @Mock
    private WeatherInfo weatherInfo;

    @Mock
    private Weather weather;

    @Test
    void updateAndReturnWeatherDescriptionThrowsException() throws WeatherInfoNotAvailiableException {
        Mockito.when(weatherInfoService.getWeatherInfo(CITY, COUNTRY)).thenReturn(weatherInfo);
        Mockito.when(weatherInfo.getWeather()).thenReturn(null);

        WeatherInfoOrchestrationServiceImpl weatherInfoOrchestrationService = new WeatherInfoOrchestrationServiceImpl(weatherInfoService, weatherInfoRepository);
        try {
            weatherInfoOrchestrationService.updateAndReturnWeatherDescription(CITY, COUNTRY);
            fail("Expected WeatherInfoNotAvailiableException");
        } catch (WeatherInfoNotAvailiableException e) {

        }
    }

    @Test
    void updateAndReturnWeatherDescriptionSuccess() throws WeatherInfoNotAvailiableException {
        Mockito.when(weatherInfoService.getWeatherInfo(CITY, COUNTRY)).thenReturn(weatherInfo);
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        Mockito.when(weatherInfo.getWeather()).thenReturn(weatherList);
        Mockito.when(weatherInfoRepository.findWeatherInfoByCityAndCountry(CITY, COUNTRY)).thenReturn(null).thenReturn(weatherInfo);

        WeatherInfoOrchestrationServiceImpl weatherInfoOrchestrationService = new WeatherInfoOrchestrationServiceImpl(weatherInfoService, weatherInfoRepository);
        weatherInfoOrchestrationService.updateAndReturnWeatherDescription(CITY, COUNTRY);

        Mockito.verify(weatherInfoService).getWeatherInfo(CITY, COUNTRY);
        Mockito.verify(weatherInfoRepository, Mockito.times(2)).findWeatherInfoByCityAndCountry(CITY, COUNTRY);
        Mockito.verify(weatherInfo).setCityCode(CITY);
        Mockito.verify(weatherInfo).setCountryCode(COUNTRY);
        Mockito.verify(weatherInfoRepository).save(weatherInfo);
        Mockito.verify(weather).getDescription();

    }

}