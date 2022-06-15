package com.bmo.projects.weathertelegrambot.component.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.component.PrepareableKeyboardRow;
import com.bmo.projects.weathertelegrambot.component.PrepareableReplyKeyboardMarkup;
import com.bmo.projects.weathertelegrambot.component.SendMessagePrepareable;
import com.bmo.projects.weathertelegrambot.component.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class MainMenu implements Menu {
    private final AbstractHideableKeyboardButton localWeatherButton;
    private final AbstractHideableKeyboardButton sendLocationButton;
    private final AbstractHideableKeyboardButton todayWeatherButton;
    private final AbstractHideableKeyboardButton sevenDaysWeatherButton;

    @Override
    public void draw(WeatherBot bot, Update update) {
        bot.executePrepareable(getMessage(update), bot, update);
    }

    private SendMessagePrepareable getMessage(Update update) {
        return SendMessagePrepareable.builder()
                .chatId(UpdateUtils.extractStringChatId(update))
                .text("""
                        Let's check the weather!
                        Send your location or location which you want through
                        attach -> location""")
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(false)
                .replyMarkup(PrepareableReplyKeyboardMarkup.builder()
                        .keyboard(Lists.newArrayList(
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        localWeatherButton,
                                        sendLocationButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        todayWeatherButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        sevenDaysWeatherButton))))
                        .oneTimeKeyboard(false)
                        .resizeKeyboard(true)
                        .selective(true)
                        .build())
                .build();
    }
}
