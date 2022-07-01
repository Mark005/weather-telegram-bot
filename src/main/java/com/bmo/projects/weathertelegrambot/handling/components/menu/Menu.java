package com.bmo.projects.weathertelegrambot.handling.components.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Menu {

    void draw(String text, WeatherBot bot, Update update);
}
