package com.challenge.service;

import com.challenge.entity.WeatherInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class WeatherInfoServiceImplTest {

    @Mock
    private WebClient.Builder builder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private Function function;

    @Mock
    private Mono mono;

    @Mock
    private WeatherInfo weatherInfo;

    private String baseUrl;

    private String apiKey;

    @Test
    public void testGetWeatherInfoThrowsSuccess() throws WeatherInfoNotAvailiableException {

        WeatherInfoServiceImpl weatherInfoServiceImplTest = new WeatherInfoServiceImpl(baseUrl, apiKey, builder);

        Mockito.when(builder.build()).thenReturn(webClient);
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(ArgumentMatchers.any(Function.class))).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(WeatherInfo.class)).thenReturn(mono);
        Mockito.when(mono.block()).thenReturn(weatherInfo);
        assertEquals(weatherInfo, weatherInfoServiceImplTest.getWeatherInfo("test", "test"));
    }
}