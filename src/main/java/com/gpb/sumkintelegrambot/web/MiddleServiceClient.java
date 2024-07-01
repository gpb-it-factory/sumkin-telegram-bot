package com.gpb.sumkintelegrambot.web;

import com.gpb.sumkintelegrambot.web.dto.AccountDto;
import com.gpb.sumkintelegrambot.web.dto.GetUserDto;
import com.gpb.sumkintelegrambot.web.dto.RegisterTransferDto;
import com.gpb.sumkintelegrambot.web.dto.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "middleServiceClient", url = "${myMiddle.url}")
public interface MiddleServiceClient {
    @PostMapping(value = "/v2/users")
    ResponseEntity<GetUserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto);

    @GetMapping("/v2/users/{id}")
    ResponseEntity<GetUserDto> getUserById(@PathVariable Long id);

    @GetMapping("/v2/users/{tgUsername}")
    ResponseEntity<GetUserDto> getUserByName(@PathVariable String tgUsername);

    @PostMapping("/v2/users/{id}/accounts")
    ResponseEntity<AccountDto> registerAccount(@PathVariable Long id, @RequestBody String accountName);

    @GetMapping("/v2/users/{id}/accounts")
    ResponseEntity<List<AccountDto>> getAccountsList(@PathVariable Long id);

    @PostMapping("/v2/transfers")
    ResponseEntity<UUID> registerTransfer(@Valid @RequestBody RegisterTransferDto registerTransferDto);

}
