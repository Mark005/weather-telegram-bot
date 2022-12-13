package com.bmo.projects.weathertelegrambot.utils.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContextVariables {
    SENDER_ID("senderId"),
    CHAT_ID("chatId"),
    UPDATE("update"),
    LOCATION("location"),
    MENU_MESSAGE("menuMessage");

    private final String value;
}
