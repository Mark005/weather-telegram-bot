package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service("registerUser")
@RequiredArgsConstructor
public class RegisterUserAction implements Action<String, String> {
    private final UserService userService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);
        Long senderId = contextData.getSenderId();
        Long chatId = contextData.getChatId();

        if (userService.findById(senderId).isEmpty()) {
            User user = User.builder()
                    .id(senderId)
                    .chatId(chatId)
                    .build();
            userService.save(user);
            ContextUtils.putValue(context,
                    ContextUtils.ContextVariables.MENU_MESSAGE,
                    """
                            I can forecast ⛅ your future! :D
                            Let's check check it together!""");
            return;
        }
        ContextUtils.putValue(context,
                ContextUtils.ContextVariables.MENU_MESSAGE,
                "Hello again, my old friend, long time no see!");
    }

}
