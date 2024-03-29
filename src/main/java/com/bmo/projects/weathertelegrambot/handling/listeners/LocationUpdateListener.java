package com.bmo.projects.weathertelegrambot.handling.listeners;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationUpdateListener implements UpdateListener {

    @Override
    public void handle(StateMachine<String, String> stateMachine, Update update) {
        Optional.ofNullable(update.getMessage())
                .map(Message::getLocation)
                .ifPresent(location -> {
                    ContextUtils.putValue(stateMachine, ContextUtils.ContextVariables.LOCATION, location);

                    stateMachine.sendEvent(Mono.just(MessageBuilder
                                    .withPayload(MenuEvent.LOCATION_CONSUMED)
                                    .build()))
                            .subscribe();
                });
    }
}
