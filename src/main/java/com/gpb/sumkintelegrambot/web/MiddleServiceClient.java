package com.gpb.sumkintelegrambot.web;

import com.gpb.sumkintelegrambot.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "middleServiceClient", url = "${myMiddle.url}")
public interface MiddleServiceClient {
    @PostMapping(value = "/v2/users")
    ResponseEntity<GetUserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto);

    @GetMapping("/v2/users/{tgId}")
    ResponseEntity<GetUserDto> getUserById(@PathVariable Long tgId);

    @GetMapping("/v2/users/tgName/{tgUsername}")
    ResponseEntity<GetUserDto> getUserByName(@PathVariable String tgUsername);

    @PostMapping("/v2/users/{id}/accounts")
    ResponseEntity<AccountDto> registerAccount(@PathVariable Long id,
                                               @Valid @RequestBody RegAccountDto regAccountDto);

    @GetMapping("/v2/users/{id}/accounts")
    ResponseEntity<AccountDto> getAccountsList(@PathVariable Long id);

    @PostMapping("/v2/transfers")
    ResponseEntity<UUID> registerTransfer(@Valid @RequestBody RegisterTransferDto registerTransferDto);

}
