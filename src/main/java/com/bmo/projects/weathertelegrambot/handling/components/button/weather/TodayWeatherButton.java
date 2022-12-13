package com.bmo.projects.weathertelegrambot.handling.components.button.weather;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.utils.context.ContextVariables;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Component
public class TodayWeatherButton extends AbstractHideableKeyboardButton {

    public TodayWeatherButton() {
        setText(ButtonEnum.TODAY_WEATHER.getButtonText());
    }

    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.TODAY_WEATHER)
                        .setHeader(ContextVariables.UPDATE.getValue(), update)
                        .build()))
                .subscribe();
    }
}
