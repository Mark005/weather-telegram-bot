package com.bmo.projects.weathertelegrambot;

import com.bmo.projects.weathertelegrambot.configs.CommandSetter;
import com.bmo.projects.weathertelegrambot.handling.UpdateHandler;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.weather.service.ForecastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public Message sendUpdateResponseMessage(String text, Update update) throws TelegramApiException {
        return execute(
                SendMessage.builder()
                        .text(text)
                        .chatId(UpdateUtils.extractStringChatId(update))
                        .build());
    }
}
