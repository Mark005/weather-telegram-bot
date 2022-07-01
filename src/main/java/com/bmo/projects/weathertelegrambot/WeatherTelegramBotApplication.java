package com.bmo.projects.weathertelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
public class WeatherTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherTelegramBotApplication.class, args);
    }

}
