package com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenMeteoResponse {

    @JsonProperty("utc_offset_seconds")
    private Integer utcOffsetSeconds;

    private Hourly hourly;

    private Daily daily;
}
