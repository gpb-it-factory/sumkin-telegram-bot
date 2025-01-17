package com.gpb.sumkintelegrambot.service.commands;

import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import com.gpb.sumkintelegrambot.web.dto.RegisterTransferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransferCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;
    private BigDecimal amountBigDecimal;

    public TransferCommand(MiddleServiceClient middleServiceClient) {
        this.middleServiceClient = middleServiceClient;
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String tgUsername = update.getMessage().getChat().getUserName();
        RegisterTransferDto registerTransferDto = getTransferDto(update);
        try {
            ResponseEntity<UUID> response = middleServiceClient.registerTransfer(
                    registerTransferDto);
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

    private RegisterTransferDto getTransferDto(Update update) {
        String text = update.getMessage().getText();
        String from = update.getMessage().getChat().getUserName();
        String amount = text.split(" ")[1];
        try {
            amountBigDecimal = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
        String to = text.split(" ")[2];
        return new RegisterTransferDto(from, to, amountBigDecimal);
    }

    private String getResponseText(ResponseEntity<UUID> response) {
        int statusCode = response.getStatusCode().value();
        return switch (statusCode) {
            case 200 -> "Перевод совершен. id транзакции: " + response.getBody();
            default -> "Незадокументированный код ответа";
        };
    }

    @Override
    public Command getCommand() {
        return Command.TRANSFER;
    }
}
