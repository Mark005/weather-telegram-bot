package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    @Override
    public CommandEnum getHandlingCommand() {
        return CommandEnum.START;
    }

    @Override
    @SneakyThrows
    public void handle(WeatherBot bot, Update update) {
        bot.execute(SendMessage.builder()
                .chatId(UpdateUtils.extractStringChatId(update))
                .text("message with keyboard")
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(false)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(List.of(
                                new KeyboardRow(
                                        List.of(KeyboardButton.builder()
                                                        .text(CommandEnum.LOCAL_WEATHER.getCommand())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text("Send my location")
                                                        .requestLocation(true)
                                                        .build())),
                                new KeyboardRow(
                                        List.of(KeyboardButton.builder()
                                                .text(CommandEnum.TODAY_WEATHER.getCommand())
                                                .build())),
                                new KeyboardRow(
                                        List.of(KeyboardButton.builder()
                                                .text(CommandEnum.FIVE_DAYS_FORECAST.getCommand())
                                                .build()))))
                        .oneTimeKeyboard(false)
                        .resizeKeyboard(true)
                        .selective(true)
                        .build())
                .build());
    }
}
