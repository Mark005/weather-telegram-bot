package com.bmo.projects.weathertelegrambot.configs;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Service
public class CommandSetterImpl implements CommandSetter {

    @Override
    public void setCommands(WeatherBot bot) {
        setCommands(bot, BotStatusEnum.ONLINE);

        Thread haltedHook = new Thread(() -> setCommands(bot, BotStatusEnum.OFFLINE));
        Runtime.getRuntime().addShutdownHook(haltedHook);
    }

    private void setCommands(WeatherBot bot, BotStatusEnum status) {
        bot.execute(getRussianCommands(status));
        bot.execute(getEnglishCommands(status));
    }

    private SetMyCommands getRussianCommands(BotStatusEnum status) {
        return SetMyCommands.builder()
                .commands(List.of(
                        BotCommand.builder()
                                .command(CommandEnum.START.getCommand())
                                .description("Начать")
                                .build(),
                        BotCommand.builder()
                                .command(CommandEnum.STATUS.getCommand())
                                .description("Статус бота: %s %s".formatted(status.getEmoji(), status.getText()))
                                .build()))
                .languageCode(LanguageEnum.RUSSIAN.getCode())
                .build();
    }

    private SetMyCommands getEnglishCommands(BotStatusEnum status) {
        return SetMyCommands.builder()
                .commands(List.of(
                        BotCommand.builder()
                                .command(CommandEnum.START.getCommand())
                                .description("Start")
                                .build(),
                        BotCommand.builder()
                                .command(CommandEnum.STATUS.getCommand())
                                .description("Bot status:  %s %s".formatted(status.getEmoji(), status.getText()))
                                .build()))
                .languageCode(LanguageEnum.ENGLISH.getCode())
                .build();
    }
}
