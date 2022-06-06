package com.bmo.projects.weathertelegrambot.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum LanguageEnum {
    UNKNOWN(""),
    RUSSIAN("ru"),
    ENGLISH("en");

    private final String code;

    @Nullable
    public static LanguageEnum getEnumByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }

        return Stream.of(LanguageEnum.values())
                .filter(languageEnum -> languageEnum.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
