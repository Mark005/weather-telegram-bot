package com.bmo.projects.weathertelegrambot.handling.components.button.location;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.MenuService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class DropLocationButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    public DropLocationButton() {
        setText(ButtonEnum.DROP_LOCATION.getButtonText());
    }

    @Override
    public void onClick(WeatherBot bot, Update update) {
        User user = userService.getById(UpdateUtils.extractSenderId(update));
        user.setLocation(null);
        userService.save(user);
        menuService.getCurrentMenu(update).draw("Location has been dropped", bot, update);
    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = userService.findById(UpdateUtils.extractSenderId(update))
                .map(User::getLocation);
        return locationOptional.isPresent();
    }
}
