package com.bmo.projects.weathertelegrambot.handling;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    void handle(WeatherBot bot, Update update);
}
