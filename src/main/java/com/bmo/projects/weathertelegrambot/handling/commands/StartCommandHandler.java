package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.component.menu.Menu;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final Menu mainMenu;

    @Override
    public CommandEnum getHandlingCommand() {
        return CommandEnum.START;
    }

    @Override
    public void handle(WeatherBot bot, Update update) {
        mainMenu.draw(bot, update);
    }
}
