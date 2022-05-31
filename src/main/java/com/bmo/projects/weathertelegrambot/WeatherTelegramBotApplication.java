package com.bmo.projects.weathertelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@SpringBootApplication
public class WeatherTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherTelegramBotApplication.class, args);
    }

}
