package com.example.mycity.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherData {
//    private double lon;
//    private double lat;
    private String main;
    private String description;
    private String icon;
    private double temp;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private int humidity;
    private double windSpeed;

    public WeatherData(String main, String description, String icon, double temp, double feelsLike, double tempMin, double tempMax, int humidity, double windSpeed) {
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }
}
