package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Component("currentWeather")
@RequiredArgsConstructor
public class CurrentWeatherAction implements Action<String, String> {
    private final WeatherBot bot;
    private final UserService userService;
    private final ForecastService forecastService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        Update update = contextData.getUpdate();

        Optional<Location> locationOptional = userService.findById(UpdateUtils.extractSenderId(update))
                .map(User::getLocation);
        if (locationOptional.isEmpty()) {
            bot.sendChatMessage("Send your location to the bot to get a forecast!", update);
            return;
        }

        Location location = locationOptional.get();
        WeatherPoint currentWeather = forecastService.getCurrentWeather(
                location.getLatitude(),
                location.getLongitude());

        String responsePattern = """
                Temperature \uD83C\uDF21️  %.1f С
                Precipitations \uD83D\uDCA7  %s мм
                Cloud cover ⛅  %s%%
                Humidity \uD83C\uDF2B   %s%%
                """;

        bot.sendChatMessage(
                responsePattern.formatted(
                        currentWeather.getTemperature(),
                        currentWeather.getPrecipitations(),
                        currentWeather.getCloudCoverPercent(),
                        currentWeather.getHumidity()),
                update);
    }
}
