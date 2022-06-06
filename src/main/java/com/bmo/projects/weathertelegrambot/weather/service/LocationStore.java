package com.bmo.projects.weathertelegrambot.weather.service;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.Optional;

public interface LocationStore {
    Location saveLocation(Long userId, Location location);

    Optional<Location> findLocationByUserId(Long userId);
}
