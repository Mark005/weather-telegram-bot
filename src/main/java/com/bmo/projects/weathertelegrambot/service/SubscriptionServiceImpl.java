package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.utils.TimezoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserService userService;

    @Override
    public boolean isUserSubscribed(Long userId) {
        return userService.findById(userId)
                .map(User::getIsSubscribed)
                .orElse(false);
    }

    @Override
    public void subscribe(Long userId, LocalTime notificationTime) {
        User user = userService.getById(userId);

        Location userLocation = user.getLocation();
        String timezoneString = TimezoneMapper.latLngToTimezoneString(
                userLocation.getLatitude(),
                userLocation.getLongitude());

        ZoneId zoneId = ZoneId.of(timezoneString);

        LocalTime localTime = LocalTime.now(zoneId);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                LocalDateTime.of(
                        LocalDate.now(),
                        notificationTime), zoneId);

        if (localTime.isAfter(notificationTime)) {
            zonedDateTime = zonedDateTime.plusDays(1);
        }

        user.setIsSubscribed(true);
        user.setNextUpdateTime(zonedDateTime);
        userService.save(user);
    }

    @Override
    public void unsubscribe(Long userId) {
        User user = userService.getById(userId);
        user.setIsSubscribed(false);
        user.setNotificationTime(null);
        user.setNextUpdateTime(null);
        userService.save(user);
    }
}
