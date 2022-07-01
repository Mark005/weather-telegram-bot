package com.bmo.projects.weathertelegrambot.handling.components.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.ForecastService;
import com.bmo.projects.weathertelegrambot.service.UserStore;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
public class SevenDaysWeatherButton extends AbstractHideableKeyboardButton {
    @Autowired
    private UserStore userStore;
    @Autowired
    private ForecastService forecastService;

    public SevenDaysWeatherButton() {
        setText(ButtonEnum.SEVEN_DAYS_WEATHER.getButtonText());
    }

    @Override
    public void onClick(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = userStore.findById(UpdateUtils.extractSenderId(update))
                .map(User::getLocation);
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
