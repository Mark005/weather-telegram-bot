package com.bmo.projects.weathertelegrambot.weather.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class LocationStoreImpl implements LocationStore {
    private Map<Long, Location> userLocationMap = new TreeMap<>();

    @Override
    public Location saveLocation(Long userId, Location location) {
        return userLocationMap.put(userId, location);
    }

    @Override
    public Optional<Location> findLocationByUserId(Long userId) {
        return Optional.ofNullable(userLocationMap.get(userId));
    }
}
