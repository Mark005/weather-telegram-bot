package com.bmo.projects.weathertelegrambot.weather.api.mapper;

import com.bmo.projects.weathertelegrambot.configs.MapStructCommonConfig;
import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.Daily;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface DailyForecastMapper {

    default List<DayForecast> map(Daily daily) {
        List<DayForecast> weatherPointList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            DayForecast newDayForecast =
                    DayForecast.builder()
                            .date(daily.getDates().get(i))
                            .maxDailyTemperature(daily.getMaxDailyTemperature().get(i))
                            .minDailyTemperature(daily.getMinDailyTemperature().get(i))
                            .precipitations(daily.getPrecipitations().get(i))
                            .precipitationHours(daily.getPrecipitationHours().get(i))
                            .maxWindSpeed(daily.getPrecipitationHours().get(i))
                            .build();

            weatherPointList.add(newDayForecast);
        }
        return weatherPointList;
    }
}
