package com.bmo.projects.weathertelegrambot.handling.commands;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.menu.Menu;
import com.bmo.projects.weathertelegrambot.model.CommandEnum;
import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final Menu mainMenu;
    private final UserService userService;

    @Override
    public CommandEnum getHandlingCommand() {
        return CommandEnum.START;
    }

    @Override
    public void handle(WeatherBot bot, Update update) {
        if (userService.findById(UpdateUtils.extractSenderId(update)).isEmpty()) {
            User user = User.builder()
                    .id(UpdateUtils.extractSenderId(update))
                    .chatId(UpdateUtils.extractChatId(update))
                    .isSubscribed(false)
                    .build();
            userService.save(user);
        }

        mainMenu.draw("""
                Let's check the weather!
                Send your location or location which you want through
                attach -> location""",
                bot, update);
    }
}
