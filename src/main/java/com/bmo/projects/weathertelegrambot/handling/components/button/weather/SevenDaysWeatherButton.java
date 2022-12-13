package com.bmo.projects.weathertelegrambot.handling.components.button.weather;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Component
public class SevenDaysWeatherButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserStore userStore;
    @Autowired
    private ForecastService forecastService;

    public SevenDaysWeatherButton() {
        setText(ButtonEnum.SEVEN_DAYS_WEATHER.getButtonText());
    }

    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.SEVEN_DAYS_WEATHER)
                        .build()))
                .subscribe();
    }
}
