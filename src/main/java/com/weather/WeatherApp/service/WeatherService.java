package com.weather.WeatherApp.service;

import com.weather.WeatherApp.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.forecast.url}")
    private String apiForeCastUrl;

    private RestTemplate template = new RestTemplate();

    public WeatherResponse getData(String city) {
        String url = apiUrl + "?key=" + apiKey + "&q=" + city;
        Root response = template.getForObject(url, Root.class);
        WeatherResponse weatherResponse = new WeatherResponse();
        // String c = response.getLocation().name; instead of this we use directly below
        // code

        weatherResponse.setCity(response.getLocation().name);
        weatherResponse.setRegion(response.getLocation().region);
        weatherResponse.setCountry(response.getLocation().country);
        String Condition = response.current.getCondition().getText();
        weatherResponse.setCondition(Condition);
        weatherResponse.setTemperature(response.getCurrent().getTemp_c());
        // New fields
        weatherResponse.setHumidity(response.getCurrent().getHumidity());
        weatherResponse.setFeelsLike(response.getCurrent().getFeelslike_c());
        weatherResponse.setWindKph(response.getCurrent().getWind_kph());
        weatherResponse.setWindDir(response.getCurrent().getWind_dir());
        weatherResponse.setUv(response.getCurrent().getUv());

        return weatherResponse;
    }

    public WeatherForeCast getForecast(String city, int days) {
        WeatherForeCast weatherforecast = new WeatherForeCast();
        WeatherResponse weatherResponse = getData(city);
        weatherforecast.setWeatherResponse(weatherResponse);
        WeatherForeCast response = new WeatherForeCast();
        response.setWeatherResponse(weatherResponse);

        List<DaysTemp> daysList = new ArrayList<>();
        String url = apiForeCastUrl + "?key=" + apiKey + "&q=" + city + "&days=" + days;
        Root apiResponse = template.getForObject(url, Root.class);
        ForeCast forecast = apiResponse.getForecast();
        ArrayList<ForeCastDay> foreCastDays = forecast.getForecastday();
        for (ForeCastDay rs : foreCastDays) {
            DaysTemp d = new DaysTemp();
            d.setDate(rs.getDate());
            d.setMinTemp(rs.getDay().mintemp_c);
            d.setMaxTemp(rs.getDay().maxtemp_c);
            d.setAvgTemp(rs.getDay().avgtemp_c);
            // New fields
            d.setChanceOfRain(rs.getDay().getDaily_chance_of_rain());
            d.setCondition(rs.getDay().getCondition().getText());
            d.setAvgHumidity(rs.getDay().getAvghumidity());
            daysList.add(d);
        }
        response.setDaysTemp(daysList);
        return response;
    }
}
