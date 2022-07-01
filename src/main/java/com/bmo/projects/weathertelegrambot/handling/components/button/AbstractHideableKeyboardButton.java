package com.bmo.projects.weathertelegrambot.handling.components.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public abstract class AbstractHideableKeyboardButton extends KeyboardButton implements Button {

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        return true;
    }
}
