package com.bmo.projects.weathertelegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPoint {
    private LocalDateTime dateTime;
    private Double temperature;
    private Integer cloudCoverPercent;
    private Double precipitations;
    private Integer humidity;
}
