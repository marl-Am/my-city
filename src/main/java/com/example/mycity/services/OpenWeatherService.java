package com.example.mycity.services;

import com.example.mycity.models.WeatherData;
import com.example.mycity.utility.PropertiesUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

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
     * @return      a singular weatherData object
     */
    @CrossOrigin
    public WeatherData getWeatherData(String city) throws Exception {

        Map<String, String> properties = PropertiesUtils.getProperties();
        String WEATHER_API_KEY = properties.get("WEATHER_API_KEY.key");

        // String city = "New York City"
        city = city.replace(" ", "%20");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.openweathermap.org/data/2.5/weather?q=" +
                city +
                "&units=imperial" +
                "&appid=" + WEATHER_API_KEY);
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            // Get the response body as a String
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            JsonNode weather = root.path("weather").get(0);
            String main = weather.path("main").asText();
            String description = weather.path("description").asText();
            String icon = weather.path("icon").asText();

            JsonNode mainNode = root.path("main");
            double temp = mainNode.path("temp").asDouble();
            double feelsLike = mainNode.path("feels_like").asDouble();
            double tempMin = mainNode.path("temp_min").asDouble();
            double tempMax = mainNode.path("temp_max").asDouble();
            int humidity = mainNode.path("humidity").asInt();

            JsonNode wind = root.path("wind");
            double windSpeed = wind.path("speed").asDouble();

            // Create a WeatherData object with the parsed values
            WeatherData weatherData = new WeatherData(main, description, icon, temp, feelsLike, tempMin, tempMax, humidity, windSpeed);
            return weatherData;

        } finally {
            response.close();
        }
    }
}
