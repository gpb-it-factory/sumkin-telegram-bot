package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PingCommand implements ICommand {
    @Override
    public SendMessage getResponseMessage(Update update) {
        return SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text("pong")
                .build();
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.PING;
    }
}