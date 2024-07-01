package com.gpb.sumkintelegrambot.web.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyErrorDto {
    private String message;
    private String type;
    private int code;
    private UUID traceId;
}
