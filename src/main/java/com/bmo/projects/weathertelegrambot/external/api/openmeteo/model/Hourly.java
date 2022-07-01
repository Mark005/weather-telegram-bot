package com.bmo.projects.weathertelegrambot.external.api.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hourly {

    private List<LocalDateTime> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature;

    private List<Double> precipitation;

    @JsonProperty("cloudcover")
    private List<Integer> cloudCover;

    @JsonProperty("relativehumidity_2m")
    private List<Integer> relativeHumidity;
}
