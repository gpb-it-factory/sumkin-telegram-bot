package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@Component
public class RegisterCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;

    public RegisterCommand(MiddleServiceClient middleServiceClient) {
        this.middleServiceClient = middleServiceClient;
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        try {
            ResponseEntity<UUID> response = middleServiceClient.registerUser(chatId);
            String responseText = getResponseText(response);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(responseText)
                    .build();
        } catch (Exception e) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Произошло что-то ужасное, но станет лучше, честно")
                    .build();
        }
    }

    private String getResponseText(ResponseEntity<UUID> response) {
        int statusCode = response.getStatusCode().value();
        if (statusCode == 204) {
            return "Регистрация прошла успешно. Ваш id: " + response.getBody();
        } else if (statusCode == 409) {
            return "Вы уже зарегистрированы";
        } else {
            return "Незадокументированный код ответа";
        }
    }


    @NotNull
    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }
}
