package by.bsuir.linguaserver.service;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;

@Service
public class TgService {

    private final TelegramBot tgBot;

    public TgService(TelegramBot tgBot) {
        this.tgBot = tgBot;
    }
}
