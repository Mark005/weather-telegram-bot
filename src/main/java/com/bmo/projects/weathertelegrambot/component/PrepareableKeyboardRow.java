package com.bmo.projects.weathertelegrambot.component;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.component.button.AbstractHideableKeyboardButton;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class PrepareableKeyboardRow extends ArrayList<AbstractHideableKeyboardButton> implements Validable, Prepareable{

    public PrepareableKeyboardRow() {
    }

    public PrepareableKeyboardRow(int initialCapacity) {
        super(initialCapacity);
    }

    public PrepareableKeyboardRow(Collection<? extends AbstractHideableKeyboardButton> c) {
        super(c);
    }

    public void prepare(WeatherBot bot, Update update) {
        this.removeIf(button -> !button.isVisible(bot, update));
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        for (AbstractHideableKeyboardButton AbstractHideableKeyboardButton : this) {
            AbstractHideableKeyboardButton.validate();
        }
    }
}
