package com.bmo.projects.weathertelegrambot.handling.components.infrastructure.button;

import com.bmo.projects.weathertelegrambot.handling.components.infrastructure.keyboard.Prepareable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public abstract class PrepareableBotApiMethod<T extends Serializable> extends BotApiMethod<T> implements Prepareable {
}
