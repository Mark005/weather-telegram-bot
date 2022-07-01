package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuStoreImpl implements MenuStore {
    private final Cache<Long, Menu> cache =
            CacheBuilder.newBuilder()
                    .maximumSize(100)
                    .expireAfterWrite(Duration.ofMinutes(5))
                    .build();

    @Override
    public void setCurrentMenu(Long userId, Menu menu) {
        cache.put(userId, menu);
    }

    @Override
    public Optional<Menu> getCurrentMenu(Long userId) {
        return Optional.ofNullable(cache.getIfPresent(userId));
    }
}
