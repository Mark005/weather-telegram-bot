package com.bmo.projects.weathertelegrambot.handling.components.button.subscribtion;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.utils.context.ContextVariables;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Component
public class UnsubscribeButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserService userService;

    public UnsubscribeButton() {
        setText(ButtonEnum.UNSUBSCRIBE.getButtonText());
    }


    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.UNSUBSCRIBE)
                        .build()))
                .subscribe();
    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        return userService.findById(UpdateUtils.extractSenderId(update))
                .map(user -> user.getIsSubscribed() &&
                        user.getLocation() != null)
                .orElse(false);
    }
}
