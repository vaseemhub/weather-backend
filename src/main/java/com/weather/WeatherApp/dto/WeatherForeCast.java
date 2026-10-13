package com.weather.WeatherApp.dto;

import java.util.List;

public class WeatherForeCast {
    private WeatherResponse weatherResponse;
 private List<DaysTemp> daysTemp;

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public List<DaysTemp> getDaysTemp() {
        return daysTemp;
    }

    public void setDaysTemp(List<DaysTemp> daysTemp) {
        this.daysTemp = daysTemp;
    }
}
