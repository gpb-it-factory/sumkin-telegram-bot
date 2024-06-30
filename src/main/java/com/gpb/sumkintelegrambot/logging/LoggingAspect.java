package com.gpb.sumkintelegrambot.logging;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* getResponseMessage(org.telegram.telegrambots.meta.api.objects.Update))")
    public SendMessage logUserAction(ProceedingJoinPoint joinPoint) {
        SendMessage sendMessage = null;
        try {
            sendMessage = (SendMessage) joinPoint.proceed();
            Update update = (Update) joinPoint.getArgs()[0];

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String userName = update.getMessage().getFrom().getUserName();
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            String methodName = signature.getMethod().getName();
            String declaringClass = signature.getMethod().getDeclaringClass().getName();

            log.info("Пользователь (имя, id): {}, {}. Сообщение пользователя: {}.  Метод: {}. Класс: {}",
                    userName, chatId, message, methodName, declaringClass);
            log.info("Отправил сообщение \"{}\" получателю {}", sendMessage.getText(),
                    sendMessage.getChatId());
        } catch (Throwable e) {
            log.info("Ошибка логирования в аспекте");
        }
        return sendMessage;
    }
}
