package com.bmo.projects.weathertelegrambot.handling.components.menu;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button.AbstractHideableKeyboardButton;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.PrepareableKeyboardRow;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.PrepareableReplyKeyboardMarkup;
import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.SendMessagePrepareable;
import com.bmo.projects.weathertelegrambot.utils.UpdateUtils;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("subscriptionMenu")
@RequiredArgsConstructor
public class SubscriptionMenu implements Action<String, String> {

    private final WeatherBot bot;
    private final AbstractHideableKeyboardButton subscribeButton;
    private final AbstractHideableKeyboardButton unsubscribeButton;
    private final AbstractHideableKeyboardButton backButton;

    @Override
    public void execute(StateContext<String, String> context) {
        Update update = ContextUtils.extractData(context).getUpdate();
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
                                        subscribeButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        unsubscribeButton)),
                                new PrepareableKeyboardRow(Lists.newArrayList(
                                        backButton))))
                        .oneTimeKeyboard(false)
                        .resizeKeyboard(true)
                        .selective(true)
                        .build())
                .build();
    }
}
