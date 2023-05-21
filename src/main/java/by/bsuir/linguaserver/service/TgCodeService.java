package by.bsuir.linguaserver.service;

import by.bsuir.linguaserver.entity.TgCode;
import by.bsuir.linguaserver.repository.TgCodeRepository;
import com.pengrad.telegrambot.model.Chat;
import jakarta.persistence.PersistenceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TgCodeService {

    private final TgCodeRepository tgCodeRepository;

    @Autowired
    public TgCodeService(TgCodeRepository tgCodeRepository) {
        this.tgCodeRepository = tgCodeRepository;
    }

    public TgCode create(Chat chat) {
        TgCode tgCode = new TgCode();
        tgCode.setChatId(chat.id());
        boolean persisted = false;
        while (!persisted) {
            try {
                tgCode.setCode(RandomStringUtils.randomAlphanumeric(16));
                tgCode = tgCodeRepository.save(tgCode);
                persisted = true;
            } catch (PersistenceException ignored) {
            }
        }
        return tgCode;
    }

    public Optional<TgCode> find(String code) {
        return tgCodeRepository.findByCode(code);
    }
}
