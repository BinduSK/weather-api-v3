package com.ibmc.weatherapiv3.restclient;

import com.ibmc.weatherapiv3.exception.ApplicationException;
import com.ibmc.weatherapiv3.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherRestClient {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    RestTemplate restTemplate = new RestTemplate();

    public OpenWeatherResponse getWeather(String city, String state, String country) {
        OpenWeatherResponse openWeatherResponse = null;
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," +
                    state + "," + country + "&units=metric&appid=79dc2d0d64e5cd2d2a41b5ae677b7c44";
            openWeatherResponse = restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                //handle 404
                log.info("Unable to retrieve weather city={},state={},country={}", city, state, country);
                throw new NotFoundException("City not found", e);
            }
            log.error("Unable to retrieve weather city={},state={},country={},error={}",
                    city, state, country, e.toString());
            throw new ApplicationException("Unable to process request", e);
        }
        return openWeatherResponse;
    }

}
