package com.example.mycity.controllers;

import com.example.mycity.models.WeatherData;
import com.example.mycity.services.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private OpenWeatherService weatherService;

    @GetMapping("/weather")
    public WeatherData getWeather(@RequestParam(value = "city") String city) {
        return weatherService.getWeatherData(city);
    }
}

