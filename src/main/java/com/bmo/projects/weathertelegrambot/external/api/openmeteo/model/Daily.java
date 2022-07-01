package com.bmo.projects.weathertelegrambot.external.api.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Daily {

    @JsonProperty("time")
    private List<LocalDate> dates;

    @JsonProperty("temperature_2m_max")
    private List<Double> maxDailyTemperature;

    @JsonProperty("temperature_2m_min")
    private List<Double> minDailyTemperature;

    @JsonProperty("precipitation_sum")
    private List<Double> precipitations;

    @JsonProperty("precipitation_hours")
    private List<Integer> precipitationHours;

    @JsonProperty("windspeed_10m_max")
    private List<Integer> maxWindSpeed;
}