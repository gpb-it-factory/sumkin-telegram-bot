package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import com.gpb.sumkintelegrambot.web.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AccountRegisterCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;

    public AccountRegisterCommand(MiddleServiceClient middleServiceClient) {
        this.middleServiceClient = middleServiceClient;
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String accountName = getAccountName(update);
        try {
            ResponseEntity<AccountDto> response = middleServiceClient.registerAccount(
                    chatId, accountName);
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

    private String getAccountName(Update update) {
        String text = update.getMessage().getText();
        return text.substring(text.indexOf(" ") + 1);
    }

    private String getResponseText(ResponseEntity<AccountDto> response) {
        int statusCode = response.getStatusCode().value();
        AccountDto body = response.getBody();
        return switch (statusCode) {
            case 201 -> "Счет успешно создан. Ваш счет: " + (body != null ? body.getAccountId() : "Unknown");
            case 409 -> "Такой счет у данного пользователя уже есть";
            default -> "Незадокументированный код ответа" + statusCode;
        };
    }

    @Override
    public Command getCommand() {
        return Command.REGACCOUNT;
    }
}
