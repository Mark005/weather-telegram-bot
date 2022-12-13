package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserStore;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sevenDaysWeather")
@RequiredArgsConstructor
public class SevenDaysWeatherAction implements Action<String, String> {
    private final WeatherBot bot;
    private final UserStore userStore;
    private final ForecastService forecastService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        userStore.findById(contextData.getSenderId())
                .map(User::getLocation)
                .ifPresentOrElse(
                        location -> {
                            List<DayForecast> sevenDaysForecast = forecastService.getSevenDaysForecast(
                                    location.getLatitude(),
                                    location.getLongitude());
                            String response = WeatherPrinterUtils.formSevenDaysForecast(sevenDaysForecast);
                            bot.sendChatMessage(response, contextData.getChatId());
                        },
                        () -> bot.sendChatMessage(
                                "Send your location to the bot to get a forecast!",
                                contextData.getChatId()));
    }
}
