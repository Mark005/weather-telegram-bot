package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.weather.service.ForecastService;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalWeatherCommandHandler implements CommandHandler {
    private final LocationStore locationStore;
    private final ForecastService forecastService;

    @Override
    public CommandEnum getHandlingCommand() {
        return CommandEnum.LOCAL_WEATHER;
    }

    @SneakyThrows
    @Override
    public void handle(WeatherBot bot, Update update) {
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
                Temperature - %.1f
                Precipitations - %s мм
                Cloud cover - %s%%
                Humidity -  %s%%
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
