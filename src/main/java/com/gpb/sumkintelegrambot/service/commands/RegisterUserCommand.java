package com.gpb.sumkintelegrambot.service.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpb.sumkintelegrambot.configuration.Command;
import com.gpb.sumkintelegrambot.service.ICommand;
import com.gpb.sumkintelegrambot.web.MiddleServiceClient;
import com.gpb.sumkintelegrambot.web.dto.GetUserDto;
import com.gpb.sumkintelegrambot.web.dto.MyErrorDto;
import com.gpb.sumkintelegrambot.web.dto.RegisterUserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.nio.ByteBuffer;

@Component
@RequiredArgsConstructor
public class RegisterUserCommand implements ICommand {

    private final MiddleServiceClient middleServiceClient;

    @Override
    public SendMessage getResponseMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String tgUsername = update.getMessage().getChat().getUserName();
        try {
            ResponseEntity<GetUserDto> response = middleServiceClient.registerUser(
                    new RegisterUserDto(chatId, tgUsername));
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

    private static MyErrorDto getMyErrorDto(FeignException e) {
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

    private String getResponseText(ResponseEntity<GetUserDto> response) {
        try {
            int statusCode = response.getStatusCode().value();
            if (statusCode == 201) {
                GetUserDto body = response.getBody();
                if (body != null) {
                    return "Регистрация прошла успешно. Ваш id: " + body.getId();
                }
            }
        } catch (Exception e) {
            return "Произошло что-то ужасное, но станет лучше, честно";
        }
        return "Незадокументированный код ответа";
    }

    @NotNull
    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }
}
