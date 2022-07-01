package com.bmo.projects.weathertelegrambot.handling.components;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Prepareable {
    void prepare(WeatherBot bot, Update update);
}
