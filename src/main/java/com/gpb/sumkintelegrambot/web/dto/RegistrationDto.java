package com.gpb.sumkintelegrambot.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
public class RegistrationDto {

    @JsonDeserialize
    private String id;

}
