package com.bmo.projects.weathertelegrambot.service;

import java.time.LocalTime;

public interface SubscriptionService {
    boolean isUserSubscribed(Long userId);

    void subscribe(Long userId, LocalTime notificationTime);

    void unsubscribe(Long userId);
}
