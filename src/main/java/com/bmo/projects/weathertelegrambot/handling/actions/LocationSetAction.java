package com.bmo.projects.weathertelegrambot.handling.actions;

import com.bmo.projects.weathertelegrambot.model.User;
import com.bmo.projects.weathertelegrambot.service.UserService;
import com.bmo.projects.weathertelegrambot.utils.TimezoneMapper;
import com.bmo.projects.weathertelegrambot.utils.context.ContextData;
import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.time.ZoneId;

@Slf4j
@Component("locationSet")
@RequiredArgsConstructor
public class LocationSetAction implements Action<String, String> {
    private final UserService userService;

    @Override
    public void execute(StateContext<String, String> context) {
        ContextData contextData = ContextUtils.extractData(context);

        User user = userService.getById(contextData.getSenderId());
        Location location = contextData.getLocation();

        String timezoneString = TimezoneMapper.latLngToTimezoneString(
                location.getLatitude(),
                location.getLongitude());
        ZoneId zoneId = ZoneId.of(timezoneString);

        user.setLocation(location);
        user.setZoneId(zoneId);
        userService.save(user);

        ContextUtils.putValue(context,
                ContextUtils.ContextVariables.MENU_MESSAGE,
                "Location updated");
    }
}
