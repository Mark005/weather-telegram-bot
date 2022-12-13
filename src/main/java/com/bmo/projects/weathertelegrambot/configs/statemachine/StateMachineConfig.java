package com.bmo.projects.weathertelegrambot.configs.statemachine;

import com.bmo.mermaidstatemachine.MermaidStateMachineModelFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.ConfigurationData;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.config.model.verifier.DefaultStateMachineModelVerifier;

import java.util.List;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Lazy
    private final StateMachineModelFactory<String, String> modelFactory;

    @Override
    public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
        model
                .withModel()
                .factory(modelFactory);
    }

    @Bean
    public StateMachineModelFactory<String, String> modelFactory(ApplicationContext context) {
        ConfigurationData<String, String> statemachineConfig = new ConfigurationData<>(
                null,
                false,
                null,
                List.of(new MenuStateMachineApplicationListener()),
                false,
                null,
                null,
                null,
                null,
                true,
                new DefaultStateMachineModelVerifier(),
                null,
                null,
                null);

        return new MermaidStateMachineModelFactory(
                "/statemachine/schema.md",
                statemachineConfig);
    }
}
