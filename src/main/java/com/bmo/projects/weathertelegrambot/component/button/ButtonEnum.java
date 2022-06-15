package com.bmo.projects.weathertelegrambot.component.button;

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
    SEVEN_DAYS_WEATHER("7 Days forecast");

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
