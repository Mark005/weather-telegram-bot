package com.bmo.projects.weathertelegrambot.service.mapper;

import com.bmo.projects.weathertelegrambot.configs.MapStructCommonConfig;
import com.bmo.projects.weathertelegrambot.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface UserMapper {

    User copy(User copy);
}
