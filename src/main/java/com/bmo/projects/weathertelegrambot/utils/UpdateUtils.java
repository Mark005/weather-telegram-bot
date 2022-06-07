package com.bmo.projects.weathertelegrambot.utils;

import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@UtilityClass
public class UpdateUtils {

    public static Long extractSenderId(Update update) {
        Message message = update.getMessage() != null ? update.getMessage() : update.getEditedMessage();
        return Optional.of(message)
                .map(Message::getFrom)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("Sender id not found!"));
    }

    public static Long extractChatId(Update update) {
        Message message = update.getMessage() != null ? update.getMessage() : update.getEditedMessage();
        return Optional.of(message)
                .map(Message::getChatId)
                .orElseThrow(() -> new RuntimeException("Chat id not found!"));
    }

    public static String extractStringChatId(Update update) {
        Message value = update.getMessage() != null ? update.getMessage() : update.getEditedMessage();
        return Optional.of(value)
                .map(Message::getChatId)
                .map(String::valueOf)
                .orElseThrow(() -> new RuntimeException("Chat id not found!"));
    }

    public static String extractMessageText(Optional<Update> updateOptional) {
        return updateOptional.map(Update::getMessage)
                .map(Message::getText)
                .orElse(null);
    }

    public static String extractMessageText(Update update) {
        return extractMessageText(Optional.of(update));
    }

    public static CommandEnum extractCommand(Optional<Update> updateOptional) {
        return updateOptional.map(Update::getMessage)
                .map(Message::getText)
                .map(CommandEnum::getEnumByCommand)
                .orElse(null);
    }

    public static CommandEnum extractCommand(Update update) {
        return extractCommand(Optional.of(update));
    }

}
