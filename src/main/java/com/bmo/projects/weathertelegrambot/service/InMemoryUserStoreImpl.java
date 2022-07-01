package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.User;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Repository
public class InMemoryUserStoreImpl implements UserStore {
    private final Map<Long, User> userMap = new TreeMap<>();

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMap.get(id))
                .map(this::getCopy);
    }

    @Override
    public User save(User user) {
        return userMap.put(user.getId(), user);
    }

    @Override
    public List<User> getByNextUpdateTimeBefore(ZonedDateTime timeAfter) {
        return userMap.values()
                .stream()
                .filter(User::getIsSubscribed)
                .filter(user -> user.getNextUpdateTime().isBefore(timeAfter))
                .map(this::getCopy)
                .toList();
    }

    private User getCopy(User user) {
        return User.builder()
                .id(user.getId())
                .chatId(user.getChatId())
                .location(getLocationCopy(user.getLocation()))
                .isSubscribed(user.getIsSubscribed())
                .nextUpdateTime(user.getNextUpdateTime())
                .build();
    }

    private Location getLocationCopy(Location location) {
        if (location == null) {
            return null;
        }
        Location copy = new Location();

        copy.setLatitude(location.getLatitude());
        copy.setLongitude(location.getLongitude());
        copy.setHorizontalAccuracy(location.getHorizontalAccuracy());
        copy.setLivePeriod(location.getLivePeriod());
        copy.setHeading(location.getHeading());
        copy.setProximityAlertRadius(location.getProximityAlertRadius());
        return copy;
    }

}
