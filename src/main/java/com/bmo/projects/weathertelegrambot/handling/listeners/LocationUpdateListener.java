package com.bmo.projects.weathertelegrambot.handling.listeners;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class LocationUpdateListener implements UpdateListener {
    private final LocationStore locationStore;

    @Override
    public void handle(WeatherBot bot, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasLocation()) {
            return;
        }

        Location location = update.getMessage().getLocation();
        locationStore.saveLocation(UpdateUtils.extractSenderId(update), location);

        bot.sendUpdateResponseMessage("Location has been changed", update);
    }
}
