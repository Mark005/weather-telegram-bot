package com.bmo.projects.weathertelegrambot.handling.components.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.PrepareableKeyboardRow;
import com.bmo.projects.weathertelegrambot.handling.components.PrepareableReplyKeyboardMarkup;
import com.bmo.projects.weathertelegrambot.handling.components.SendMessagePrepareable;
import com.bmo.projects.weathertelegrambot.handling.components.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.service.MenuService;
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

    private final MenuService menuService;
    private final AbstractHideableKeyboardButton localWeatherButton;
    private final AbstractHideableKeyboardButton sendLocationButton;
    private final AbstractHideableKeyboardButton todayWeatherButton;
    private final AbstractHideableKeyboardButton sevenDaysWeatherButton;
    private final AbstractHideableKeyboardButton subscribeButton;
    private final AbstractHideableKeyboardButton unsubscribeButton;

    @Override
    public void draw(String text, WeatherBot bot, Update update) {
        menuService.setCurrentMenu(update, this);
        bot.executePrepareable(getMessage(update, text), bot, update);
    }

    private SendMessagePrepareable getMessage(Update update, String text) {
        return SendMessagePrepareable.builder()
                .chatId(UpdateUtils.extractStringChatId(update))
                .text(text)
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
                                        sevenDaysWeatherButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        subscribeButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        unsubscribeButton))))
                        .oneTimeKeyboard(false)
                        .resizeKeyboard(true)
                        .selective(true)
                        .build())
                .build();
    }
}
