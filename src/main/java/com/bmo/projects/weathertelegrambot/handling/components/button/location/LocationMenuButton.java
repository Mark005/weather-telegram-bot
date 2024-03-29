package com.bmo.projects.weathertelegrambot.handling.components.button.location;

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
public class LocationMenuButton extends AbstractHideableKeyboardButton {

    public LocationMenuButton() {
        setText(ButtonEnum.LOCATION.getButtonText());
    }

    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        ContextUtils.putValue(
                stateMachine,
                ContextUtils.ContextVariables.MENU_MESSAGE,
                "Let's select your location");

        stateMachine.sendEvent(
                        Mono.just(MessageBuilder
                                .withPayload(MenuEvent.LOCATION_MENU)
                                .build()))
                .subscribe();
    }
}
