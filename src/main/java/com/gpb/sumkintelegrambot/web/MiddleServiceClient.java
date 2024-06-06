package com.gpb.sumkintelegrambot.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "middleServiceClient", url = "${myMiddle.url}")
public interface MiddleServiceClient {
    @PostMapping("/users")
    ResponseEntity<UUID> registerUser(long chatId);
}
