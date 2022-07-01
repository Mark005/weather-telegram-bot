package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserStore {
    Optional<User> findById(Long id);

    User save(User user);

    List<User> getByNextUpdateTimeBefore(ZonedDateTime timeAfter);
}
