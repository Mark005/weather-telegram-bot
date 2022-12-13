package com.bmo.projects.weathertelegrambot.configs.statemachine;

import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StateMachineCacheService {

    StateMachine<String, String> getOrCreateForUser(Long userId, Update update);
}
