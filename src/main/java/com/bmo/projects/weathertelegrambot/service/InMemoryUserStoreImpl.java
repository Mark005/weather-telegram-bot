package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class InMemoryUserStoreImpl implements UserStore {
    private final UserMapper mapper;
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMap.get(id))
                .map(this::getCopy);
    }

    @Override
    public List<User> getAll() {
        return userMap.values().stream().toList();
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
        return mapper.copy(user);
    }

}
