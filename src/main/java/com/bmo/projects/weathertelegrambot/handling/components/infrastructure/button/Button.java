package com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Button {

    void onClick(StateMachine<String, String> stateMachine, Update update);

    boolean isVisible(WeatherBot bot, Update update);
}
