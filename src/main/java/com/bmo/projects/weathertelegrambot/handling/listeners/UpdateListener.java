package com.bmo.projects.weathertelegrambot.handling.listeners;

import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateListener {
    void handle(StateMachine<String, String> stateMachine, Update update);
}
