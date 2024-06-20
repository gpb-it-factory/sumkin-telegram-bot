package com.gpb.sumkintelegrambot.web;

import com.gpb.sumkintelegrambot.web.dto.AccountDto;
import com.gpb.sumkintelegrambot.web.dto.TransferDto;
import com.gpb.sumkintelegrambot.web.dto.UserDto;
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
    @PostMapping("/v2/users")
    ResponseEntity<UUID> registerUser(@RequestBody UserDto userDto);

    @GetMapping("/v2/users/{id}")
    ResponseEntity<UUID> getUserById(@PathVariable Long id);

    @GetMapping("/v2/users/{tgUsername}")
    ResponseEntity<UUID> getUserByName(@PathVariable String tgUsername);

    @PostMapping("/v2/users/{id}/accounts")
    ResponseEntity<UUID> registerAccount(@PathVariable Long id, @RequestBody String accountName);

    @GetMapping("/v2/users/{id}/accounts")
    ResponseEntity<List<AccountDto>> getAccountsList(@PathVariable Long id);

    @PostMapping("/v2/transfers")
    ResponseEntity<UUID> registerTransfer(@RequestBody TransferDto transferDto);
}
