package com.example.telegrambot.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Prayer {
    private String region;
    private String date;
    private String weekday;
    private Times times;
}
