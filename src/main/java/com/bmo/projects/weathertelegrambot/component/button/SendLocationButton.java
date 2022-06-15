package com.bmo.projects.weathertelegrambot.component.button;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.weather.service.LocationStore;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class SendLocationButton extends AbstractHideableKeyboardButton {
    private final LocationStore locationStore;

    public SendLocationButton(LocationStore locationStore) {
        this.locationStore = locationStore;
        setText(ButtonEnum.SEND_MY_LOCATION.getButtonText());
        setRequestLocation(true);
    }


    @Override
    public void onClick(WeatherBot bot, Update update) {

    }

    @Override
    public boolean isVisible(WeatherBot bot, Update update) {
        Optional<Location> locationOptional = locationStore.findLocationByUserId(UpdateUtils.extractSenderId(update));
        return locationOptional.isEmpty();
    }
}
