package com.bmo.projects.weathertelegrambot.component.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Button {

    void onClick(WeatherBot bot, Update update);

    boolean isVisible(WeatherBot bot, Update update);
}
