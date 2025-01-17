package com.gpb.sumkintelegrambot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    private UUID id;
    private String accountName;
    private BigDecimal amount;
}
