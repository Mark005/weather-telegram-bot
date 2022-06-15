package com.bmo.projects.weathertelegrambot.component.button;

import com.bmo.projects.weathertelegrambot.component.Prepareable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public abstract class PrepareableBotApiMethod<T extends Serializable> extends BotApiMethod<T> implements Prepareable {
}
