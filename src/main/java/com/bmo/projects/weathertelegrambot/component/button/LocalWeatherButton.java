package com.bmo.projects.weathertelegrambot.component.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.weather.service.ForecastService;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class LocalWeatherButton extends AbstractHideableKeyboardButton {
    private final LocationStore locationStore;
    private final ForecastService forecastService;

    public LocalWeatherButton(LocationStore locationStore, ForecastService forecastService) {
        this.locationStore = locationStore;
        this.forecastService = forecastService;
        setText(ButtonEnum.LOCAL_WEATHER.getButtonText());
    }


    @Override
    public void onClick(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = locationStore.findLocationByUserId(UpdateUtils.extractSenderId(update));
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
