package com.gpb.sumkintelegrambot.handler;

import com.gpb.sumkintelegrambot.service.ICommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MessageHandler {
    private final Map<String, ICommand> commands = new HashMap<>();

    public MessageHandler(List<ICommand> commands) {
        commands.forEach(command -> {
            if (this.commands.containsKey(command.getCommand().name)) {
                log.error("Duplicate command name: {}, classes: {}, {}",
                        command.getCommand(), this.commands.get(command.getCommand().name), command);
                throw new IllegalStateException("Duplicate command name");
            }
            this.commands.put(command.getCommand().name, command);
        });
    }

    public SendMessage createResponse(Update update) {
        return processCommand(update);
    }

    private SendMessage processCommand(Update update) {
        String message = update.getMessage().getText().toLowerCase();
        ICommand command = commands.get(message);
        if (command == null) {
            return notFoundCommand(update);
        }
        return command.getResponseMessage(update);
    }

    private SendMessage notFoundCommand(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                """
                Команда не найдена"!
                Используйте /help для просмотра списка доступных команд.
                """
        );
    }
}
