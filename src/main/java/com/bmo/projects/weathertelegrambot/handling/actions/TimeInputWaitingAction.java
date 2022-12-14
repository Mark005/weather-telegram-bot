package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.WeatherBot;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("timeInputWaiting")
@RequiredArgsConstructor
public class TimeInputWaitingAction implements Action<String, String> {
    private final WeatherBot bot;

    @Override
    public void execute(StateContext<String, String> context) {
        bot.sendChatMessage("Print time when you want get notifications",
                ContextUtils.extractData(context).getChatId());
    }
}
