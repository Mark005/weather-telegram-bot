package com.bmo.projects.weathertelegrambot.handling.components.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class LocalWeatherButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserService userService;
    @Autowired
    private ForecastService forecastService;

    public LocalWeatherButton() {
        setText(ButtonEnum.LOCAL_WEATHER.getButtonText());
    }


    @Override
    public void onClick(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = userService.findById(UpdateUtils.extractSenderId(update))
                .map(User::getLocation);
        if (locationOptional.isEmpty()) {
            bot.sendUpdateResponseMessage("Send your location to the bot to get a forecast!", update);
            return;
        }

        Location location = locationOptional.get();
        WeatherPoint currentWeather = forecastService.getCurrentWeather(
                location.getLatitude(),
                location.getLongitude());

        String responsePattern = """
                Temperature \uD83C\uDF21️ - %.1f С
                Precipitations \uD83D\uDCA7 - %s мм
                Cloud cover ⛅ - %s%%
                Humidity \uD83C\uDF2B -  %s%%
                """;

        bot.sendUpdateResponseMessage(
                responsePattern.formatted(
                        currentWeather.getTemperature(),
                        currentWeather.getPrecipitations(),
                        currentWeather.getCloudCoverPercent(),
                        currentWeather.getHumidity()),
                update);
    }
}
