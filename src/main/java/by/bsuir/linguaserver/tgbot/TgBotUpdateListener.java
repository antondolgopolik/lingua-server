package by.bsuir.linguaserver.tgbot;

import by.bsuir.linguaserver.service.TgCodeService;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class TgBotUpdateListener implements UpdatesListener {

    private final TelegramBot tgBot;
    private final TgCodeService tgCodeService;

    @Autowired
    public TgBotUpdateListener(@Lazy TelegramBot tgBot,
                               TgCodeService tgCodeService) {
        this.tgBot = tgBot;
        this.tgCodeService = tgCodeService;
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            processMessage(update.message());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processMessage(Message message) {
        String response = prepareResponse(message);
        SendMessage sendMessage = new SendMessage(message.chat().id(), response);
        tgBot.execute(sendMessage, new SendCallback());
    }

    private String prepareResponse(Message message) {
        if (Objects.equals(message.text(), "/code")) {
            return tgCodeService.create(message.from()).getCode();
        } else {
            return "Unknown command";
        }
    }

    private static class SendCallback implements Callback<SendMessage, SendResponse> {

        @Override
        public void onResponse(SendMessage sendMessage, SendResponse sendResponse) {
            if (!sendResponse.isOk()) {
                log.error("Code send error occurred");
            }
        }

        @Override
        public void onFailure(SendMessage sendMessage, IOException e) {
            log.error("Code send error occurred", e);
        }
    }
}
