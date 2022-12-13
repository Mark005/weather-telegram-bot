package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("todaysWeather")
@RequiredArgsConstructor
public class TodayWeatherAction implements Action<String, String> {
    private final WeatherBot bot;
    private final UserService userService;
    private final ForecastService forecastService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        userService.findById(contextData.getSenderId())
                .map(User::getLocation)
                .ifPresentOrElse(
                        location -> {
                            List<WeatherPoint> todayDetailedForecast = forecastService.getTodayDetailedForecast(
                                    location.getLatitude(),
                                    location.getLongitude());
                            String message = WeatherPrinterUtils.formNearestForecast(todayDetailedForecast);
                            bot.sendChatMessage(message, contextData.getChatId());
                        },
                        () -> bot.sendChatMessage(
                                "Send your location to the bot to get a forecast!",
                                contextData.getChatId()));
    }

}
