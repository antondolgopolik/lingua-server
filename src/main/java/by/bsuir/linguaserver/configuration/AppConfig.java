package by.bsuir.linguaserver.configuration;

import by.bsuir.linguaserver.tgbot.TgBotUpdateListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class AppConfig {

    @Bean
    public TelegramBot telegramBot(@Value("${tgbot.token}") String tgBotToken,
                                   @Autowired TgBotUpdateListener updateListener) {
        TelegramBot telegramBot = new TelegramBot(tgBotToken);
        GetUpdates getUpdates = new GetUpdates().allowedUpdates("message");
        telegramBot.setUpdatesListener(updateListener, getUpdates);
        return telegramBot;
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        return filter;
    }
}
