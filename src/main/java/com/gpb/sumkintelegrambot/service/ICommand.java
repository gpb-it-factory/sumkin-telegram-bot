package com.gpb.sumkintelegrambot.service;

import com.gpb.sumkintelegrambot.configuration.Command;
import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ICommand {
    SendMessage getResponseMessage(Update update);
    @NonNull
    Command getCommand();
}
