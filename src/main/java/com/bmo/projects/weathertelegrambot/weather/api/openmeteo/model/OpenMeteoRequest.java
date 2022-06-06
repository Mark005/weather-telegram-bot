package com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenMeteoRequest {
    private Double latitude;
    private Double longitude;
    private String hourly;
    private String daily;
    private String timezone;
}
