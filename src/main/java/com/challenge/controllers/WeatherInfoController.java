package com.challenge.controllers;

import com.challenge.model.Message;
import com.challenge.service.WeatherInfoNotAvailiableException;
import com.challenge.service.WeatherInfoOrchestrationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherInfoController {

    private final WeatherInfoOrchestrationService weatherInfoService;

    @GetMapping("/weather/{country}/{city}")
    @ApiOperation(value = "Returns description of weather ", response = Message.class)
    @ApiImplicitParam(value = "apikey", name = "apikey", required = true, paramType = "query")
    public Message getWeatherDescription(@PathVariable String country, @PathVariable String city) throws WeatherInfoNotAvailiableException {
        return weatherInfoService.updateAndReturnWeatherDescription(city, country);
    }

}
