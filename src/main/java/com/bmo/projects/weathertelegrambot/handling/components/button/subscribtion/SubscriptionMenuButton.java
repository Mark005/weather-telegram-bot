package com.bmo.projects.weathertelegrambot.handling.components.button.subscribtion;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Component
public class SubscriptionMenuButton extends AbstractHideableKeyboardButton {

    @Autowired
    private UserService userService;

    public SubscriptionMenuButton() {
        setText(ButtonEnum.SUBSCRIPTION_MENU.getButtonText());
    }

    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
        ContextUtils.putValue(stateMachine,
                ContextUtils.ContextVariables.MENU_MESSAGE,
                "Let's subscribe you on forecast news");
        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(MenuEvent.SUBSCRIPTION_MENU)
                        .build()))
                .subscribe();
    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        return userService.findById(UpdateUtils.extractSenderId(update))
                .map(user -> user.getLocation() != null)
                .orElse(false);
    }
}
