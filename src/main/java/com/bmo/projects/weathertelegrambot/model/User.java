package com.bmo.projects.weathertelegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private Long chatId;
    private Location location;
    private ZoneId zoneId;
    @Builder.Default
    private Boolean isSubscribed = false;
    private LocalTime notificationTime;
    private ZonedDateTime nextUpdateTime;
}
