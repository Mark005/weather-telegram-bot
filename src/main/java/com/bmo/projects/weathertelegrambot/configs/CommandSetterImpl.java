package com.bmo.projects.weathertelegrambot.configs;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Service
public class CommandSetterImpl implements CommandSetter {

    @Override
    @SneakyThrows
    public void setCommands(WeatherBot bot) {
        bot.execute(
                SetMyCommands.builder()
                .commands(List.of(new BotCommand("/forecast", "fff")))
                .build());
    }
}
