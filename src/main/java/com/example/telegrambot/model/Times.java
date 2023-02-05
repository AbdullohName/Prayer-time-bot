package com.example.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Times {
    private String tong_saharlik;
    private String quyosh;
    private String peshin;
    private String asr;
    private String shom_iftor;
    private String hufton;
}
