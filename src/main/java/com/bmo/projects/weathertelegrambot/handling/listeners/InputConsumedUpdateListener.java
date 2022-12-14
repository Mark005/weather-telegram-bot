package com.bmo.projects.weathertelegrambot.handling.listeners;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InputConsumedUpdateListener implements UpdateListener {

    @Override
    public void handle(StateMachine<String, String> stateMachine, Update update) {
        ContextUtils.putValue(stateMachine, ContextUtils.ContextVariables.UPDATE, update);
        ContextUtils.putValue(stateMachine,
                ContextUtils.ContextVariables.MESSAGE_TEXT,
                Optional.ofNullable(UpdateUtils.extractMessageText(update))
                        .orElse(""));
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.INPUT_CONSUMED)
                        .build()))
                .subscribe();
    }
}
