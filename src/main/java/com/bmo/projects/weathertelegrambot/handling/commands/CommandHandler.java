package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {

    CommandEnum getHandlingCommand();

    void handle(WeatherBot bot, Update update);
}
