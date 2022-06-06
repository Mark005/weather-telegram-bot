package com.bmo.projects.weathertelegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayForecast {

    private LocalDate date;

    private Double maxDailyTemperature;

    private Double minDailyTemperature;

    private Double precipitations;

    private Integer precipitationHours;

    private Integer maxWindSpeed;

}
