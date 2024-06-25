package com.gpb.sumkintelegrambot.web;

import com.gpb.sumkintelegrambot.web.dto.AccountDto;
import com.gpb.sumkintelegrambot.web.dto.RegistrationDto;
import com.gpb.sumkintelegrambot.web.dto.TransferDto;
import com.gpb.sumkintelegrambot.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "middleServiceClient", url = "${myMiddle.url}")
public interface MiddleServiceClient {
    @PostMapping(value = "/v2/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RegistrationDto> registerUser(@RequestBody UserDto userDto);

    @GetMapping("/v2/users/{id}")
    UUID getUserById(@PathVariable Long id);

    @GetMapping("/v2/users/{tgUsername}")
    ResponseEntity<UUID> getUserByName(@PathVariable String tgUsername);

    @PostMapping("/v2/users/{id}/accounts")
    ResponseEntity<UUID> registerAccount(@PathVariable Long id, @RequestBody String accountName);

    @GetMapping("/v2/users/{id}/accounts")
    ResponseEntity<List<AccountDto>> getAccountsList(@PathVariable Long id);

    @PostMapping("/v2/transfers")
    ResponseEntity<UUID> registerTransfer(@RequestBody TransferDto transferDto);

}
