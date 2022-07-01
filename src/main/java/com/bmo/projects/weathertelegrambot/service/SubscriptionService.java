package com.bmo.projects.weathertelegrambot.service;

public interface SubscriptionService {
    boolean isUserSubscribed(Long userId);

    void subscribe(Long userId);

    void unsubscribe(Long userId);
}
