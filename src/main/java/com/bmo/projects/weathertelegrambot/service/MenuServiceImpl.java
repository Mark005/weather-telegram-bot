package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuStore menuStore;
    @Lazy
    private final Menu mainMenu;

    @Override
    public void setCurrentMenu(Update update, Menu menu) {
        Long userId = UpdateUtils.extractSenderId(update);
        menuStore.setCurrentMenu(userId, menu);
    }

    @Override
    public Menu getCurrentMenu(Update update) {
        Long userId = UpdateUtils.extractSenderId(update);
        return menuStore.getCurrentMenu(userId)
                .orElse(mainMenu);
    }
}
