package com.example.telegrambot;

import com.example.telegrambot.feign.PrayerFeign;
import com.example.telegrambot.model.Prayer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/prayer")
@RestController
@RequiredArgsConstructor
public class Controller {

    private final PrayerFeign feign;
    @GetMapping
    public Prayer get() {
        return feign.getDay("Toshkent");
    }
}
