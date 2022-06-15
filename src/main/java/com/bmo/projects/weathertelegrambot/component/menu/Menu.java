package com.bmo.projects.weathertelegrambot.component.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Menu {
    void draw(WeatherBot bot, Update update);
}
