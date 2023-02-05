package com.example.telegrambot.config;

import feign.okhttp.OkHttpClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class BotConfig {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
    @Value("${telegram.botName}")
    String botUserName;

    @Value("${telegram.botToken}")
    String token;
}
