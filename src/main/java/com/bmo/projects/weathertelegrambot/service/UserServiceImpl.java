package com.bmo.projects.weathertelegrambot.service;


import com.bmo.projects.weathertelegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStore userStore;

    @Override
    public User getById(Long id) {
        return userStore.findById(id)
                .orElseThrow();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userStore.findById(id);
    }

    @Override
    public User save(User user) {
        return userStore.save(user);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return users.stream()
                .map(userStore::save)
                .toList();
    }

    @Override
    public List<User> getAllUsersToSendForecast() {
        return userStore.getByNextUpdateTimeBefore(ZonedDateTime.now());
    }
}
