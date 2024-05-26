package com.gpb.sumkintelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@Slf4j
public class CommandsService {

    private static final String PING = "/ping";
    private final TelegramClient telegramClient;

    public CommandsService(@Value("#{environment.GPB_BOT_TOKEN_SUMKIN}") String token)  {

        this.telegramClient = new OkHttpTelegramClient(token);
    }

    public void switchService(String message_text, long chatId) {
        if (message_text.startsWith("/")) {
            switch (message_text) {
                case PING -> sendReply(chatId, "pong");
                default -> sendReply(chatId,
                        "Мне пока что нечего на это ответить");
            }
        }
    }

    private void sendReply(long chatId, String message_text) {

        SendMessage message = SendMessage
                .builder()
                .chatId(chatId)
                .text(message_text)
                .build();
        try {
            telegramClient.execute(message);
            log.info("Отправил сообщение \"{}\" получателю {}", message_text, chatId);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения \"{}\" получателю {}", message_text, chatId);
        }
    }
}
