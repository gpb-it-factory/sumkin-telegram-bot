package com.gpb.sumkintelegrambot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class AccountDto {

    private final Long accountId;
    private final String accountName;
    private final BigDecimal amount;
}
