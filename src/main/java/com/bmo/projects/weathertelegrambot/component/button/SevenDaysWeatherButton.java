package com.bmo.projects.weathertelegrambot.component.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import com.bmo.projects.weathertelegrambot.weather.service.ForecastService;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
public class SevenDaysWeatherButton extends AbstractHideableKeyboardButton {
    private final LocationStore locationStore;
    private final ForecastService forecastService;

    public SevenDaysWeatherButton(LocationStore locationStore, ForecastService forecastService) {
        this.locationStore = locationStore;
        this.forecastService = forecastService;
        setText(ButtonEnum.SEVEN_DAYS_WEATHER.getButtonText());
    }

    @Override
    public void onClick(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = locationStore.findLocationByUserId(UpdateUtils.extractSenderId(update));
        if (locationOptional.isEmpty()) {
            bot.sendUpdateResponseMessage("Send your location to the bot to get a forecast!", update);
            return;
        }

        Location location = locationOptional.get();
        List<DayForecast> sevenDaysForecast = forecastService.getSevenDaysForecast(
                location.getLatitude(),
                location.getLongitude());

        String response = WeatherPrinterUtils.formSevenDaysForecast(sevenDaysForecast);
        bot.sendUpdateResponseMessage(response, update);
    }
}
