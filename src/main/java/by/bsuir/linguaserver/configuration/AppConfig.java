package by.bsuir.linguaserver.configuration;

import by.bsuir.linguaserver.tgbot.TgBotUpdateListener;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableScheduling
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

    @Bean
    public Module javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Module jdk8Module() {
        return new Jdk8Module();
    }
}
