package com.bmo.projects.weathertelegrambot.handling.components.button.location;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LocationMenuButton extends AbstractHideableKeyboardButton {
    @Autowired
    private Menu locationMenu;

    public LocationMenuButton() {
        setText(ButtonEnum.LOCATION.getButtonText());
    }

    @Override
    public void onClick(WeatherBot bot, Update update) {
        locationMenu.draw("Location menu", bot, update);
    }
}
