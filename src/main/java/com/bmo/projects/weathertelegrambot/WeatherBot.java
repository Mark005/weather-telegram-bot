package com.bmo.projects.weathertelegrambot;

import com.bmo.projects.weathertelegrambot.component.button.PrepareableBotApiMethod;
import com.bmo.projects.weathertelegrambot.configs.CommandSetter;
import com.bmo.projects.weathertelegrambot.handling.UpdateHandler;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingBot {

    @Value("${telegram.weather-bot.token}")
    private final String token;

    @Value("${telegram.weather-bot.username}")
    private final String username;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommandSetter commandSetter;
    private final UpdateHandler updateHandler;


    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onRegister() {
        commandSetter.setCommands(this);
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(update));
        updateHandler.handle(this, update);
    }

    public Message sendUpdateResponseMessage(String text, Update update) {
        return execute(
                SendMessage.builder()
                        .text(text)
                        .chatId(UpdateUtils.extractStringChatId(update))
                        .build());
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        try {
            return super.execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Serializable, Method extends PrepareableBotApiMethod<T>> T executePrepareable(Method method,
                                                                                                    WeatherBot bot,
                                                                                                    Update update) {
        method.prepare(bot, update);
        return execute(method);
    }
}
