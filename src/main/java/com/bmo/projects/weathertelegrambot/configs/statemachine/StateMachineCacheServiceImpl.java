package com.bmo.projects.weathertelegrambot.configs.statemachine;

import com.bmo.projects.weathertelegrambot.utils.context.ContextUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateMachineCacheServiceImpl implements StateMachineCacheService {
    private final StateMachineFactory<String, String> stateMachineFactory;

    private final Cache<Long, StateMachine<String, String>> contexts =
            CacheBuilder.newBuilder()
                    .maximumSize(100)
                    .expireAfterWrite(Duration.ofMinutes(5))
                    .removalListener((RemovalListener<Long, StateMachine<String, String>>)
                            notification -> {
                                notification.getValue().stopReactively().subscribe();
                                log.info("Statemachine with id:{} nas been stopped",notification.getValue().getId());
                            })
                    .build();

    @Override
    @SneakyThrows
    public StateMachine<String, String> getOrCreateForUser(Long userId, Update update) {
        return Optional.ofNullable(contexts.getIfPresent(userId))
                .orElseGet(() -> {
                    StateMachine<String, String> stateMachine = stateMachineFactory.getStateMachine();
                    ContextUtils.fillData(stateMachine, update);
                    stateMachine.startReactively().subscribe();
                    contexts.put(userId, stateMachine);
                    return stateMachine;
                });
    }



}
