package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface CommandHandler {

    CommandEnum getHandlingCommand();

    void handle(WeatherBot bot, Update update);
}
