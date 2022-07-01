package com.bmo.projects.weathertelegrambot.handling.components.button.subscribtion;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.service.MenuService;
import com.bmo.projects.weathertelegrambot.service.SubscriptionService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnsubscribeButton extends AbstractHideableKeyboardButton {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    public UnsubscribeButton() {
        setText(ButtonEnum.UNSUBSCRIBE.getButtonText());
    }


    @Override
    public void onClick(WeatherBot bot, Update update) {
        subscriptionService.unsubscribe(UpdateUtils.extractSenderId(update));
        menuService.getCurrentMenu(update).draw("Has been unsubscribed!", bot, update);
    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        return userService.findById(UpdateUtils.extractSenderId(update))
                .map(user -> user.getIsSubscribed() &&
                        user.getLocation() != null)
                .orElse(false);
    }
}
