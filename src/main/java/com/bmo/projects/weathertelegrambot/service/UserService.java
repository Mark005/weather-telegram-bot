package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getById(Long id);

    Optional<User> findById(Long id);

    User save(User user);

    List<User> saveAll(List<User> users);

    List<User> getAllUsersToSendForecast();
}
