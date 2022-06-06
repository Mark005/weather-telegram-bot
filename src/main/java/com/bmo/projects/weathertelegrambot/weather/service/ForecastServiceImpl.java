package com.bmo.projects.weathertelegrambot.weather.service;

import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.TimezoneMapper;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.OpenMeteoClient;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.Hourly;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.OpenMeteoResponse;
import com.bmo.projects.weathertelegrambot.weather.service.mapper.WeatherPointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForecastServiceImpl implements ForecastService {
    private final OpenMeteoClient openMeteoClient;
    private final WeatherPointMapper weatherPointMapper;

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

        return weatherPointMapper.map(forecast.getHourly());
    }

    @Override
    public List<WeatherPoint> getTodayDetailedForecast(double latitude, double longitude) {
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

        String usersTimezone = TimezoneMapper.latLngToTimezoneString(latitude, longitude);
        log.info("User timeZone - {}", usersTimezone);

        List<WeatherPoint> weatherPointsAfterCurrentTime =
                weatherPointMapper.map(forecast.getHourly())
                        .stream()
                        .filter(weatherPoint ->
                                LocalDateTime.now(ZoneId.of(usersTimezone))
                                        .isBefore(weatherPoint.getDateTime()))
                        .toList();

        return IntStream.range(0, weatherPointsAfterCurrentTime.size())
                .filter(n -> n % 3 == 0)
                .mapToObj(weatherPointsAfterCurrentTime::get)
                .limit(4)
                .toList();
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
