package com.example.telegrambot.feign;

import com.example.telegrambot.config.ClientConfiguration;
import com.example.telegrambot.model.Prayer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Islom",url = "https://islomapi.uz/api/present",configuration = ClientConfiguration.class)
public interface PrayerFeign {
    @RequestMapping(method = RequestMethod.GET,value = "day",produces = "application/json")
    Prayer getDay(@RequestParam String region);
}
