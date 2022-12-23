package com.example.mycity.services;

import com.example.mycity.models.WeatherData;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenWeatherService {
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private ObjectMapper mapper;

    /**
     * This method returns a WeatherData object, otherwise it throws an exception.
     *
     * @param  city the text input written in the search bar of the React frontend
     * @return      weatherData
     */
    public WeatherData getWeatherData(String city) throws Exception {
        // API Location: https://api.openweathermap.org/data/2.5/weather?q=

        // Replace YOUR_API_KEY with your actual API key
        String apiKey = "";
        // city is meant to be text input from react search bar
//        String city = "New York";
        // Replace CITY_NAME with the name of the city you want to get weather data for
        city = city.replace(" ", "%20");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=imperial" + "&appid=" + apiKey);
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            // Get the response body as a String
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            // Access the values of the JSON object
            JsonNode coord = root.path("coord");
            double lon = coord.path("lon").asDouble();
            double lat = coord.path("lat").asDouble();
//            System.out.println("Longitude: " + lon + ", Latitude: " + lat);

            JsonNode weather = root.path("weather").get(0);
            String main = weather.path("main").asText();
            String description = weather.path("description").asText();
            String icon = weather.path("icon").asText();
//            System.out.println("Main: " + main + ", Description: " + description + ", Icon: " + icon);

            JsonNode mainNode = root.path("main");
            double temp = mainNode.path("temp").asDouble();
            double feelsLike = mainNode.path("feels_like").asDouble();
            double tempMin = mainNode.path("temp_min").asDouble();
            double tempMax = mainNode.path("temp_max").asDouble();
            int humidity = mainNode.path("humidity").asInt();
//            System.out.println("Temperature: " + temp + "°F, Feels Like: " + feelsLike + "°F, Min Temp: " + tempMin + "°F, Max Temp: " + tempMax + "°F, Humidity: " + humidity + "%");

            JsonNode wind = root.path("wind");
            double windSpeed = wind.path("speed").asDouble();
//            System.out.println("Wind Speed: " + windSpeed + " MPH");

            // Create a WeatherData object with the parsed values
            WeatherData weatherData = new WeatherData(lon, lat, main, description, icon, temp, feelsLike, tempMin, tempMax, humidity, windSpeed);
            return weatherData;

        } finally {
            response.close();
        }
    }
}
