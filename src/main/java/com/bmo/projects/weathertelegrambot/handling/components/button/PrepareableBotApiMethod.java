package com.bmo.projects.weathertelegrambot.handling.components.button;

import com.bmo.projects.weathertelegrambot.handling.components.Prepareable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public abstract class PrepareableBotApiMethod<T extends Serializable> extends BotApiMethod<T> implements Prepareable {
}
