package com.bmo.projects.weathertelegrambot.handling.components.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
public class TodayWeatherButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserService userService;
    @Autowired
    private ForecastService forecastService;

    public TodayWeatherButton() {
        setText(ButtonEnum.TODAY_WEATHER.getButtonText());
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
        List<WeatherPoint> todayDetailedForecast = forecastService.getTodayDetailedForecast(
                location.getLatitude(),
                location.getLongitude());

        String response = WeatherPrinterUtils.formNearestForecast(todayDetailedForecast);
        bot.sendUpdateResponseMessage(response, update);
    }
}
