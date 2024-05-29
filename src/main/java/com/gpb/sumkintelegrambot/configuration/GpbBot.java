package com.gpb.sumkintelegrambot.configuration;

import com.gpb.sumkintelegrambot.service.CommandsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@Slf4j
public class GpbBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private static final String PING = "/ping";

    private final String token;
    private final CommandsService commandsService;

    public GpbBot(@Value("#{environment.GPB_BOT_TOKEN_SUMKIN}") String token, CommandsService commandsService) {
        this.token = token;
        this.commandsService = commandsService;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            commandsService.switchService(message_text, chatId);
        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        log.info("Бот запущен, статус: " + botSession.isRunning());
    }

}
