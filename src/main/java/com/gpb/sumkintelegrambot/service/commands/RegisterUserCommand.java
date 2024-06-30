package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import com.gpb.sumkintelegrambot.web.dto.getUserDto;
import com.gpb.sumkintelegrambot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RegisterUserCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;

    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String tgUsername = update.getMessage().getChat().getUserName();
        try {
            ResponseEntity<getUserDto> response = middleServiceClient.registerUser(
                    new UserDto(chatId, tgUsername));
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

    private String getResponseText(ResponseEntity<getUserDto> response) {
        int statusCode = response.getStatusCode().value();
        return switch (statusCode) {
            case 201 -> "Регистрация прошла успешно. Ваш id: " + response.getBody().getId();
            case 409 -> "Вы уже зарегистрированы";
            default -> "Незадокументированный код ответа";
        };
    }

    @NotNull
    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }
}
