package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MenuService {
    void setCurrentMenu(Update update, Menu menu);

    Menu getCurrentMenu(Update update);
}
