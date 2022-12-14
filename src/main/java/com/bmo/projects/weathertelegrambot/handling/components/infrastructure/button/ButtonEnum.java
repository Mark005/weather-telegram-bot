package com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ButtonEnum {

    LOCATION("Location"),
    SEND_MY_LOCATION("Send my location"),
    DROP_LOCATION("Drop location"),
    BACK("Back"),
    CURRENT_WEATHER("Current weather"),
    TODAY_WEATHER("Today's weather"),
    SEVEN_DAYS_WEATHER("7 Days forecast"),

    SUBSCRIPTION_MENU("Subscribe on daily weather"),
    SUBSCRIBE("Subscribe"),
    UNSUBSCRIBE("Unsubscribe");
    private final String buttonText;
}
