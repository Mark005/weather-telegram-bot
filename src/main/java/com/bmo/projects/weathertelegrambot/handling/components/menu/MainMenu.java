package com.bmo.projects.weathertelegrambot.handling.components.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.PrepareableKeyboardRow;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.PrepareableReplyKeyboardMarkup;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.SendMessagePrepareable;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("mainMenu")
@RequiredArgsConstructor
public class MainMenu implements Action<String, String> {
    private final WeatherBot bot;
    private final AbstractHideableKeyboardButton currentWeatherButton;
    private final AbstractHideableKeyboardButton locationMenuButton;
    private final AbstractHideableKeyboardButton todayWeatherButton;
    private final AbstractHideableKeyboardButton sevenDaysWeatherButton;
    private final AbstractHideableKeyboardButton subscribeButton;
    private final AbstractHideableKeyboardButton unsubscribeButton;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        Update update = contextData.getUpdate();
        ContextUtils.extractAndErase(context, ContextUtils.ContextVariables.MENU_MESSAGE, String.class)
                .ifPresent(message -> bot.executePrepareable(getMessage(update, message), update));
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
                                        currentWeatherButton,
                                        locationMenuButton)),
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
