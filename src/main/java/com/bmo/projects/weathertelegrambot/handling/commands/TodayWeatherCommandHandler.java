package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import com.bmo.projects.weathertelegrambot.weather.service.ForecastService;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodayWeatherCommandHandler implements CommandHandler {
    private final LocationStore locationStore;
    private final ForecastService forecastService;

    @Override
    public CommandEnum getHandlingCommand() {
        return CommandEnum.TODAY_WEATHER;
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
        List<WeatherPoint> todayDetailedForecast = forecastService.getTodayDetailedForecast(
                location.getLatitude(),
                location.getLongitude());

        String response = WeatherPrinterUtils.formNearestForecast(todayDetailedForecast);
        bot.sendUpdateResponseMessage(response, update);
    }
}
