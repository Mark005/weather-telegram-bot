package com.bmo.projects.weathertelegrambot.handling;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.component.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.commands.CommandHandler;
import com.bmo.projects.weathertelegrambot.handling.listeners.UpdateListener;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdateHandlerImpl implements UpdateHandler {
    private final List<UpdateListener> updateListeners;
    private final List<CommandHandler> commandHandlers;

    @Qualifier("textToButton")
    private final Map<String, AbstractHideableKeyboardButton> textToButton;

    @Override
    public void handle(WeatherBot bot, Update update) {
        updateListeners.forEach(updateListener -> updateListener.handle(bot, update));

        CommandEnum command = UpdateUtils.extractCommand(update);
        if (command == null) {
            return;
        }

        List<CommandHandler> commandHandlers = this.commandHandlers.stream()
                .filter(commandHandler -> commandHandler.getHandlingCommand() == command)
                .toList();
        if (CollectionUtils.isEmpty(commandHandlers)) {
            bot.sendUpdateResponseMessage("Sorry, but command no implemented yet", update);
        }
        commandHandlers.forEach(commandHandler -> commandHandler.handle(bot, update));

        AbstractHideableKeyboardButton button = textToButton.get(UpdateUtils.extractMessageText(update));
        if (button != null) {
            button.onClick(bot, update);
        }
    }
}
