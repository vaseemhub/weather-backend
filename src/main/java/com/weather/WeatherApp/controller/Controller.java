package com.weather.WeatherApp.controller;

import com.weather.WeatherApp.dto.Root;
import com.weather.WeatherApp.dto.WeatherForeCast;
import com.weather.WeatherApp.dto.WeatherResponse;
import com.weather.WeatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = { "http://localhost:5173", "https://weather-app-two-gamma-65.vercel.app",
        "https://*.vercel.app" }, allowedHeaders = "*")

public class Controller {
    @Autowired
    private WeatherService service;

    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city) {
        return service.getData(city);
    }

    @GetMapping("/forecast")
    public WeatherForeCast getForecast(@RequestParam String city, @RequestParam int days) {
        return service.getForecast(city, days);
    }

}
