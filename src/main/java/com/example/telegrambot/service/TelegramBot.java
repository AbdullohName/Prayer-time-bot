package com.example.telegrambot.service;

import com.example.telegrambot.feign.PrayerFeign;
import com.example.telegrambot.model.Prayer;
import com.vdurmont.emoji.EmojiParser;
import com.example.telegrambot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Component
@Slf4j
//@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfig config;
    private final PrayerFeign feign;
    static final String HELP_TEXT = "This bot is created to send a random joke from the database each time you request it.\n\n" +
            "You can execute commands from the main menu on the left or by typing commands manually\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /joke to get a random joke\n\n" +
            "Type /settings to list available settings to configure\n\n" +
            "Type /help to see this message again\n";
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private JavaRepository javaRepository;

    public TelegramBot(BotConfig config,PrayerFeign feign) {
        this.config = config;
        this.feign = feign;

        List<BotCommand> listOfCommands = new ArrayList<>();
//        listOfCommands.add(new BotCommand("/start","get a welcome message"));
//        listOfCommands.add(new BotCommand("/joke","get a random joke"));
//        listOfCommands.add(new BotCommand("/help","info how to use this bot"));
//        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        listOfCommands.add(new BotCommand("/toshkent","get a region Tashkent"));
        listOfCommands.add(new BotCommand("/samarqand","get a region Samarkand"));
        listOfCommands.add(new BotCommand("/guliston","get a region Sirdaryo"));
        listOfCommands.add(new BotCommand("/jizzax","get a region Jizzax"));
        listOfCommands.add(new BotCommand("/navoiy","get a region Navoiy"));
        listOfCommands.add(new BotCommand("/buxoro","get a region Buxoro"));
//        listOfCommands.add(new BotCommand("/farg'ona","get a region Farg'ona"));
//        listOfCommands.add(new BotCommand("/qo'qon","get a region Qo'qon"));
//        listOfCommands.add(new BotCommand("/marg'ilon","get a region Marg'ilon"));
        listOfCommands.add(new BotCommand("/andijon","get a region Andijon"));
//        listOfCommands.add(new BotCommand("/termiz","get a region Termiz"));
//        listOfCommands.add(new BotCommand("/urganch","get a region Urganch"));
        HashMap<String,String> hashMap = getHashMap(listOfCommands);
        try {
            this.execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));

        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private HashMap<String,String> getHashMap(List<BotCommand> list) {
        HashMap<String,String> hashMap = new HashMap<>();
        for(BotCommand command: list) {
            hashMap.put(command.getCommand(),command.getCommand() + command.getDescription());
        }
        return hashMap;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            try {
                sendMessage(prayerTime(messageText.substring(1)),chatId);
            } catch (Exception e) {
                sendMessage(e.getMessage(),chatId);
            }
//            showStart(chatId,update.getMessage().getChat().getFirstName());
//            switch (messageText) {
//                case "/start" -> showStart(chatId,update.getMessage().getChat().getFirstName());
////                case "/joke" ->
//                default -> commandNotFound(chatId);
//            }
        } else {
            commandNotFound(chatId);
        }
    }
    public String parseString(Prayer prayer) {
        return String.format("Region: %s\n\ndate: %s\n\nweekday: %s\n\ntong_saharlik: %s\n\nquyosh: %s\n\npeshin: %s\n\nasr: %s\n\nshom_iftor: %s\n\nhufton: %s\n\n"
                ,prayer.getRegion(),prayer.getDate(),prayer.getWeekday(),
                        prayer.getTimes().getTong_saharlik(),prayer.getTimes().getQuyosh(),prayer.getTimes().getPeshin(),prayer.getTimes().getAsr(),prayer.getTimes().getShom_iftor(),prayer.getTimes().getHufton());
    }

    public void showStart(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode(
                String.format("Hi, %s! :smile: Nice to meet you! I am a Simple Random Joke Bot created by Abdullah Khasimov from proj3c.io \n",name));
        sendMessage(answer,chatId);
    }

    public String prayerTime(String message) {
//        try {
        return parseString(feign.getDay(message));
//        } catch (Exception e) {
//            return e.getMessage();
//        }
    }

    private void sendMessage(String textToSend, long chatId) {
        SendMessage message = new SendMessage();   // Create a message object object
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
    private void commandNotFound(long chatId) {
        String answer = EmojiParser.parseToUnicode(
                "Command not recognized, please verify and try again :stuck_out_tongue_winking_eye: ");
        sendMessage(answer,chatId);
    }
}
