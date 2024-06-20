package com.gpb.sumkintelegrambot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto{

    private final Long id;
    private final String tgUsername;
}