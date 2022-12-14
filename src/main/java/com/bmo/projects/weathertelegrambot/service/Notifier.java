package com.bmo.projects.weathertelegrambot.service;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.configs.properties.NotificationProperties;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import com.bmo.projects.weathertelegrambot.utils.WeatherPrinterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class Notifier {
    private final UserService userService;
    private final ForecastService forecastService;
    private final WeatherBot bot;
    private final NotificationProperties notificationProperties;


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelayString = "${notification.check-sec}")
    public void notifyUser() {
        userService.getAllUsersToSendForecast()
                .stream()
                .peek(this::send)
                .peek(user -> user.setNextUpdateTime(
                        user.getNextUpdateTime().plus(1, ChronoUnit.DAYS)))
                .forEach(userService::save);
    }

    private void send(User user) {
        Location location = user.getLocation();
        List<WeatherPoint> todayDetailedForecast = forecastService.getTodayDetailedForecast(
                location.getLatitude(),
                location.getLongitude());

        String response = WeatherPrinterUtils.formNearestForecast(todayDetailedForecast);

        bot.execute(SendMessage.builder()
                .text(response)
                .chatId(user.getChatId().toString())
                .build());
    }
}
