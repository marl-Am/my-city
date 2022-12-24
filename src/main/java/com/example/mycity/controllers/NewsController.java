//package com.example.mycity.controllers;
//
//import com.example.mycity.models.NewsData;
//import com.example.mycity.models.WeatherData;
//import com.example.mycity.services.OpenNewsService;
//import com.example.mycity.services.OpenWeatherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class NewsController {
//
//    @Autowired
//    private OpenNewsService newsService;
//
//    @GetMapping("/news")
//    public NewsData getNews(@RequestParam(value = "city") String city) {
//        return newsService.getNewsData(city);
//    }
//}
