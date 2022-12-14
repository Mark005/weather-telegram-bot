package com.bmo.projects.weathertelegrambot.utils.context;

import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.experimental.UtilityClass;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.util.Optional;

import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.CHAT_ID;
import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.LOCATION;
import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.MESSAGE_TEXT;
import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.PARSED_TIME;
import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.SENDER_ID;
import static com.bmo.projects.weathertelegrambot.utils.context.ContextUtils.ContextVariables.UPDATE;

@UtilityClass
public class ContextUtils {

    public enum ContextVariables {
        SENDER_ID,
        CHAT_ID,
        UPDATE,
        MESSAGE_TEXT,
        LOCATION,
        MENU_MESSAGE,
        PARSED_TIME;
    }

    public ContextData extractData(StateContext<String, String> context) {
        Long senderId = (Long) context.getExtendedState().getVariables().get(SENDER_ID);
        Long chatId = (Long) context.getExtendedState().getVariables().get(CHAT_ID);
        String messageText = (String) context.getExtendedState().getVariables().get(MESSAGE_TEXT);
        Location location = (Location) context.getExtendedState().getVariables().get(LOCATION);
        Update update = (Update) context.getExtendedState().getVariables().get(UPDATE);
        LocalTime parsedTime = (LocalTime) context.getExtendedState().getVariables().get(PARSED_TIME);
        return ContextData.builder()
                .senderId(senderId)
                .chatId(chatId)
                .messageText(messageText)
                .location(location)
                .update(update)
                .parsedTime(parsedTime)
                .build();
    }

    public <T> T extractAndErase(StateContext<String, String> context, ContextVariables key) {
        T value = (T) context.getExtendedState().getVariables().get(key);
        context.getExtendedState().getVariables().remove(key);
        return value;
    }

    public <T> Optional<T> extractAndErase(StateContext<String, String> context, ContextVariables key, Class<T> type) {
        T value = (T) context.getExtendedState().getVariables().get(key);
        context.getExtendedState().getVariables().remove(key);
        return Optional.ofNullable(value);
    }

    public void fillData(StateMachine<String, String> stateMachine, Update update) {
        stateMachine.getExtendedState().getVariables().put(SENDER_ID, UpdateUtils.extractSenderId(update));
        stateMachine.getExtendedState().getVariables().put(CHAT_ID, UpdateUtils.extractChatId(update));
        stateMachine.getExtendedState().getVariables().put(UPDATE, update);
    }

    public void putValue(StateMachine<String, String> stateMachine, ContextVariables key, Object value) {
        stateMachine.getExtendedState().getVariables().put(key, value);
    }
    public void putValue(StateContext<String, String> context, ContextVariables key, Object value) {
        context.getExtendedState().getVariables().put(key, value);
    }


}
