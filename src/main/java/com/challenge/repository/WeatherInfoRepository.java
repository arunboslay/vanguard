package com.challenge.repository;

import com.challenge.entity.WeatherInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WeatherInfoRepository extends CrudRepository<WeatherInfo, Integer> {

    @Query("select w from WeatherInfo w where w.cityCode = :city and w.countryCode = :country")
    WeatherInfo findWeatherInfoByCityAndCountry(String city, String country);


}
