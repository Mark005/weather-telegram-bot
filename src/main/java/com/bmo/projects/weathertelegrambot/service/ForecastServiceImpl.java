package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.external.api.mapper.DailyForecastMapper;
import com.bmo.projects.weathertelegrambot.external.api.mapper.WeatherPointMapper;
import com.bmo.projects.weathertelegrambot.external.api.openmeteo.OpenMeteoClient;
import com.bmo.projects.weathertelegrambot.external.api.openmeteo.model.Hourly;
import com.bmo.projects.weathertelegrambot.external.api.openmeteo.model.OpenMeteoResponse;
import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.TimezoneMapper;
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
    private final DailyForecastMapper dailyForecastMapper;

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
                null,
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
                null,
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
                .limit(5)
                .toList();
    }

    @Override
    public List<DayForecast> getSevenDaysForecast(double latitude, double longitude) {
        OpenMeteoResponse forecast = openMeteoClient.getForecast(
                latitude,
                longitude,
                null,
                List.of("temperature_2m_max",
                        "temperature_2m_min",
                        "precipitation_sum",
                        "precipitation_hours",
                        "windspeed_10m_max"),
                TimezoneMapper.latLngToTimezoneString(latitude, longitude));

        return dailyForecastMapper.map(forecast.getDaily());
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
                null,
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
