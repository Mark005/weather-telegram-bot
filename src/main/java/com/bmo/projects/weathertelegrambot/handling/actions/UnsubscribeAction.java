package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.service.SubscriptionService;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("unsubscribe")
@RequiredArgsConstructor
public class UnsubscribeAction implements Action<String, String> {
    private final SubscriptionService subscriptionService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);

        subscriptionService.unsubscribe(contextData.getSenderId());

        ContextUtils.putValue(context, ContextUtils.ContextVariables.MENU_MESSAGE,
                "Unsubscribed");
    }
}
