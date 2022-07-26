package com.ibmc.weatherapiv3.service;

import com.ibmc.weatherapiv3.domain.Weather;
import com.ibmc.weatherapiv3.restclient.OpenWeatherResponse;
import com.ibmc.weatherapiv3.restclient.OpenWeatherRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    OpenWeatherRestClient openWeatherRestClient;
    public Weather getWeather(String city,String state,String country){
        Weather weather = new Weather();
        weather.setCity(city);
        weather.setState(state);
        weather.setCountry(country);
        OpenWeatherResponse openWeatherResponse = openWeatherRestClient.getWeather(city, state, country);
        weather.setCurrentTemperature(openWeatherResponse.getMain().getTemp());
        return weather;
    }


}
