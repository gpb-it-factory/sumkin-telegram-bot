package com.gpb.sumkintelegrambot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SumkinTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SumkinTelegramBotApplication.class, args);
    }

}
