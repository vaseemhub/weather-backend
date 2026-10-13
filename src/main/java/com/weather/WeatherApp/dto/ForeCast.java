package com.weather.WeatherApp.dto;

import java.util.ArrayList;

public class ForeCast{
    public ArrayList<ForeCastDay> forecastday;

    public ArrayList<ForeCastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(ArrayList<ForeCastDay> forecastday) {
        this.forecastday = forecastday;
    }
}