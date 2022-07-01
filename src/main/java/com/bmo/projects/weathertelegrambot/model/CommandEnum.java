package com.bmo.projects.weathertelegrambot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {
    START("/start"),
    STATUS("/status");

    private final String command;

    @Nullable
    public static CommandEnum getEnumByCommand(String command) {
        if (!StringUtils.hasText(command)) {
            return null;
        }

        return Stream.of(CommandEnum.values())
                .filter(commandEnum -> commandEnum.getCommand().equals(command))
                .findFirst()
                .orElse(null);
    }
}
