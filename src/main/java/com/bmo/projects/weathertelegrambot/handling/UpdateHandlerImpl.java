package com.bmo.projects.weathertelegrambot.handling;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.configs.statemachine.StateMachineCacheService;
import com.bmo.projects.weathertelegrambot.handling.commands.CommandHandler;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.listeners.UpdateListener;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateHandlerImpl implements UpdateHandler {

    private final StateMachineCacheService stateMachineCacheService;
    private final Map<CommandEnum, CommandHandler> commandHandlerMap;
    @Qualifier("textToButton")
    private final Map<String, AbstractHideableKeyboardButton> textToButton;
    private final List<UpdateListener> updateListeners;

    @Override
    public void handle(WeatherBot bot, Update update) {
        StateMachine<String, String> stateMachine =
                stateMachineCacheService.getOrCreateForUser(UpdateUtils.extractSenderId(update), update);

        CommandHandler commandHandler = Optional.ofNullable(UpdateUtils.extractCommand(update))
                .map(commandHandlerMap::get)
                .orElse(null);
        if (commandHandler != null) {
            commandHandler.handle(stateMachine, update);
            return;
        }

        AbstractHideableKeyboardButton button = Optional.ofNullable(UpdateUtils.extractMessageText(update))
                .map(textToButton::get).orElse(null);
        if (button != null) {
            button.onClick(stateMachine, update);
            return;
        }

        updateListeners.forEach(updateListener -> updateListener.handle(stateMachine, update));
    }
}
