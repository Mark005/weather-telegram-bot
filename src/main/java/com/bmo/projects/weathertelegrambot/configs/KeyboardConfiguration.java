package com.bmo.projects.weathertelegrambot.configs;

import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class KeyboardConfiguration {

    @Bean
    Map<String, AbstractHideableKeyboardButton> textToButton(List<AbstractHideableKeyboardButton> buttons) {
        Map<String, AbstractHideableKeyboardButton> map = buttons.stream()
                .collect(Collectors.toMap(AbstractHideableKeyboardButton::getText,
                        Function.identity()));

        if (map.size() < buttons.size()) {
            throw new IllegalStateException("Two or more buttons have the same names");
        }
        return map;
    }
}
