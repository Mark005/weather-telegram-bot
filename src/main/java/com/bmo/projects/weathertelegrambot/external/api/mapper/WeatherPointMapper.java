package com.bmo.projects.weathertelegrambot.external.api.mapper;

import com.bmo.projects.weathertelegrambot.configs.MapStructCommonConfig;
import com.bmo.projects.weathertelegrambot.external.api.openmeteo.model.Hourly;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface WeatherPointMapper {

    default List<WeatherPoint> map(Hourly hourly) {
        List<WeatherPoint> weatherPointList = new ArrayList<>();
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
}
