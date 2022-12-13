package com.bmo.projects.weathertelegrambot.handling.components.button.location;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class SendLocationButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserService userService;

    public SendLocationButton() {
        setText(ButtonEnum.SEND_MY_LOCATION.getButtonText());
        setRequestLocation(true);
    }


    @Override
    public void onClick(StateMachine<String, String> stateMachine, Update update) {
    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = userService.findById(UpdateUtils.extractSenderId(update))
                .map(User::getLocation);
        return locationOptional.isEmpty();
    }
}
