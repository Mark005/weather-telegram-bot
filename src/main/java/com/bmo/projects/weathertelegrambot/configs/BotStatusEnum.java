package com.bmo.projects.weathertelegrambot.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotStatusEnum {
    ONLINE("\uD83D\uDFE2", "ONLINE"),
    OFFLINE("\uD83D\uDD34", "OFFLINE");

    private final String emoji;
    private final String text;
}
