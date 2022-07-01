package com.bmo.projects.weathertelegrambot.handling.components.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ButtonEnum {
    SEND_MY_LOCATION("Send my location"),
    LOCAL_WEATHER("Current weather"),
    TODAY_WEATHER("Today's weather"),
    SEVEN_DAYS_WEATHER("7 Days forecast"),
    SUBSCRIBE("Subscribe on daily weather"),
    UNSUBSCRIBE("Unsubscribe from daily weather");

    private final String buttonText;

    @Nullable
    public static ButtonEnum getEnumByCommand(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }

        return Stream.of(ButtonEnum.values())
                .filter(buttonEnum -> buttonEnum.getButtonText().equals(text))
                .findFirst()
                .orElse(null);
    }
}
