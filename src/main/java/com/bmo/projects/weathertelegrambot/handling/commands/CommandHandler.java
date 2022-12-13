package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {

    CommandEnum getHandlingCommand();

    void handle(StateMachine<String, String> bot, Update update);
}
