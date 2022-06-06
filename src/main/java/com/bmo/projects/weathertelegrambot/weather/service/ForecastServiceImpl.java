package com.bmo.projects.weathertelegrambot.weather.service;

import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.TimezoneMapper;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.OpenMeteoClient;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.Hourly;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.OpenMeteoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForecastServiceImpl implements ForecastService {
    private final OpenMeteoClient openMeteoClient;

    @Override
    public List<WeatherPoint> getFulForecast(double latitude, double longitude) {

        OpenMeteoResponse forecast = openMeteoClient.getForecast(
                latitude,
                longitude,
                List.of("temperature_2m",
                        "precipitation",
                        "cloudcover",
                        "pressure_msl",
                        "relativehumidity_2m"),
                "weathercode",
                TimezoneMapper.latLngToTimezoneString(latitude, longitude));

        List<WeatherPoint> weatherPointList = new ArrayList<>();
        Hourly hourly = forecast.getHourly();
        for (int i = 0; i < 168; i++) {
            WeatherPoint newWeatherPoint =
                    WeatherPoint.builder()
                            .dateTime(hourly.getTime().get(i))
                            .temperature(hourly.getTemperature().get(i))
                            .precipitations(hourly.getPrecipitation().get(i))
                            .cloudCoverPercent(hourly.getCloudCover().get(i))
                            .humidity(hourly.getRelativeHumidity().get(i))
                            .build();

            weatherPointList.add(newWeatherPoint);
        }

        return weatherPointList;
    }

    @Override
    public WeatherPoint getCurrentWeather(double latitude, double longitude) {
        String usersTimezone = TimezoneMapper.latLngToTimezoneString(latitude, longitude);
        log.info("User timeZone - {}", usersTimezone);

        OpenMeteoResponse forecast = openMeteoClient.getForecast(
                latitude,
                longitude,
                List.of("temperature_2m",
                        "precipitation",
                        "cloudcover",
                        "pressure_msl",
                        "relativehumidity_2m"),
                "weathercode",
                usersTimezone);

        Hourly hourly = forecast.getHourly();

        int index = hourly.getTime()
                .indexOf(LocalDateTime.now(ZoneId.of(usersTimezone))
                        .truncatedTo(ChronoUnit.HOURS));

        return WeatherPoint.builder()
                .dateTime(hourly.getTime().get(index))
                .temperature(hourly.getTemperature().get(index))
                .precipitations(hourly.getPrecipitation().get(index))
                .cloudCoverPercent(hourly.getCloudCover().get(index))
                .humidity(hourly.getRelativeHumidity().get(index))
                .build();
    }

}
