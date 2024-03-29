package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;

import java.util.List;

public interface ForecastService {
    List<WeatherPoint> getFulForecast(double latitude, double longitude);

    List<WeatherPoint> getTodayDetailedForecast(double latitude, double longitude);

    List<DayForecast> getSevenDaysForecast(double latitude, double longitude);

    WeatherPoint getCurrentWeather(double latitude, double longitude);
}
