package com.gpb.sumkintelegrambot.service.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import com.gpb.sumkintelegrambot.web.dto.AccountDto;
import com.gpb.sumkintelegrambot.web.dto.MyErrorDto;
import com.gpb.sumkintelegrambot.web.dto.RegAccountDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.nio.ByteBuffer;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountRegisterCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;


    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String accountName = getAccountName(update);
        try {
            ResponseEntity<AccountDto> response = middleServiceClient.registerAccount(
                    chatId, new RegAccountDto(accountName));
            log.info("имя аккаунта: " + accountName);
            String responseText = getResponseText(response);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(responseText)
                    .build();
        } catch (FeignException e) {
            MyErrorDto myErrorDto = getMyErrorDto(e);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(myErrorDto.getMessage())
                    .build();
        }
    }

        private static MyErrorDto getMyErrorDto (FeignException e){
            try {
                ByteBuffer feignResponseBody = e.responseBody()
                        .orElseThrow(() -> new RuntimeException("Response body is null"));
                byte[] responseBodyBytes = new byte[feignResponseBody.remaining()];
                feignResponseBody.get(responseBodyBytes);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBodyBytes, MyErrorDto.class);
            } catch (Exception ex) {
                throw new RuntimeException("Произошло что-то ужасное, но станет лучше, честно");
            }
        }

        private String getAccountName (Update update){
            String text = update.getMessage().getText();
            String accountName = text.split(" ")[1];
            if (accountName==null){
                throw new IllegalArgumentException("Не указано имя аккаунта");
            }
            return text.split(" ")[1];
        }

        private String getResponseText (ResponseEntity < AccountDto > response) {
            try {
                int statusCode = response.getStatusCode().value();
                if (statusCode == 201) {
                    AccountDto body = response.getBody();
                    if (body != null) {
                        return "Счет успешно создан. Ваш счет: "
                                + body.getId();
                    }
                }
            } catch (Exception e) {
                return "Произошло что-то ужасное, но станет лучше, честно";
            }
            return "Незадокументированный код ответа";
        }

        @NotNull
        @Override
        public Command getCommand () {
            return Command.REGACCOUNT;
        }
    }
