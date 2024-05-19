package com.gpb.middle.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Slf4j
@PropertySource("classpath:secret.keys")
public class CommandsService {
    private final TelegramClient telegramClient;

    public CommandsService(@Value("${token}") String token)  {
        this.telegramClient = new OkHttpTelegramClient(token);
    }

    public void sendReply(long chatId, String message_text) {

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
