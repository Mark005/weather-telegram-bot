package com.bmo.projects.weathertelegrambot.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalTime;

@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    @NotNull
    private LocalTime notificationTime;
    @NotNull
    private Integer checkSec;
    @NotNull
    private Duration schedule;
}
