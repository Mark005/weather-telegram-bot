package com.bmo.projects.weathertelegrambot.handling.listeners;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.MenuService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class LocationUpdateListener implements UpdateListener {
    private final MenuService menuService;
    private final UserService userService;

    @Override
    public void handle(WeatherBot bot, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasLocation()) {
            return;
        }

        Location location = update.getMessage().getLocation();

        User user = userService.getById(UpdateUtils.extractSenderId(update));
        user.setLocation(location);

        userService.save(user);

        menuService.getCurrentMenu(update).draw("Location has been changed", bot, update);
    }
}
