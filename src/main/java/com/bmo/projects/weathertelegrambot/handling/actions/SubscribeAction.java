package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.configs.statemachine.MenuEvent;
import com.bmo.projects.weathertelegrambot.service.SubscriptionService;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

@Slf4j
@Component("subscribe")
@RequiredArgsConstructor
public class SubscribeAction implements Action<String, String> {
    private final SubscriptionService subscriptionService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        String messageWithTime = contextData.getMessageText();

        try {
            Long senderId = contextData.getSenderId();
            subscriptionService.subscribe(senderId, LocalTime.parse(messageWithTime));

            ContextUtils.putValue(context, ContextUtils.ContextVariables.MENU_MESSAGE,
                    "Notification time has been set");

            context.getStateMachine().sendEvent(Mono.just(MessageBuilder
                            .withPayload(MenuEvent.SUCCESS)
                            .build()))
                    .subscribe();
        } catch (Exception e) {
            context.getStateMachine().sendEvent(Mono.just(MessageBuilder
                            .withPayload(MenuEvent.ERROR)
                            .build()))
                    .subscribe();
        }
    }
}
