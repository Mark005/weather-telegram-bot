package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;

import java.util.Optional;

public interface MenuStore {
    void setCurrentMenu(Long userId, Menu menu);

    Optional<Menu> getCurrentMenu(Long userId);
}
