package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements ICommand {
    private final String responseText;

    public HelpCommand() {
        StringBuilder builder = new StringBuilder("""
                Доступные команды:
                """);
        for (Command command : Command.values()) {
            builder.append(command.name);
            builder.append(" - ");
            builder.append(command.description);
            builder.append("\n");
        }

        responseText = builder.toString();
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        return SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(responseText)
                .build();
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.HELP;
    }
}
