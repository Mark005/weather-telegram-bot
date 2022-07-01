package com.bmo.projects.weathertelegrambot.handling.components.button.location;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.button.ButtonEnum;
import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BackButton extends AbstractHideableKeyboardButton {
    @Autowired
    @Lazy
    private Menu mainMenu;

    public BackButton() {
        setText(ButtonEnum.BACK.getButtonText());
    }

    @Override
    public void onClick(WeatherBot bot, Update update) {
        mainMenu.draw("Returned to main menu", bot, update);
    }
}
