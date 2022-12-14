package com.bmo.projects.weathertelegrambot.configs.statemachine;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuEvent {
    public static final String BACK = "backEvent";

    public static final String CURRENT_WEATHER = "currentWeatherEvent";
    public static final String TODAY_WEATHER = "todaysWeatherEvent";
    public static final String SEVEN_DAYS_WEATHER = "sevenDaysWeatherEvent";

    public static final String LOCATION_CONSUMED = "locationConsumedEvent";

    public static final String DROP_LOCATION = "dropLocationEvent";
    public static final String LOCATION_MENU = "locationMenuEvent";

    public static final String SUBSCRIPTION_MENU = "subscriptionMenuEvent";
    public static final String SUBSCRIBE = "subscribeEvent";
    public static final String UNSUBSCRIBE = "unsubscribeEvent";
    public static final String INPUT_CONSUMED = "inputConsumedEvent";
    public static final String ERROR = "errorEvent";
    public static final String SUCCESS = "successEvent";

}
