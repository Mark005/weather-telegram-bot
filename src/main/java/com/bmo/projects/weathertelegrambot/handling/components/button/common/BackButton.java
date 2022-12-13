package com.bmo.projects.weathertelegrambot.handling.components.button.common;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Component
public class BackButton extends AbstractHideableKeyboardButton {
    public BackButton() {
        setText(ButtonEnum.BACK.getButtonText());
    }

    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        ContextUtils.putValue(stateMachine,
                ContextUtils.ContextVariables.MENU_MESSAGE,
                "Return");
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.BACK)
                        .build()))
                .subscribe();
    }
}
