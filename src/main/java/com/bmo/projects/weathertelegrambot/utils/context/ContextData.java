package com.bmo.projects.weathertelegrambot.utils.context;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;

@Data
@Builder
public class ContextData {
    private Long senderId;
    private Long chatId;
    private String messageText;
    private Location location;
    private Update update;
    private LocalTime parsedTime;
}
