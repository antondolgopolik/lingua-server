package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.TgCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TgCodeRepository extends JpaRepository<TgCode, Long> {

    Optional<TgCode> findByChatId(Long chatId);

    Optional<TgCode> findByCode(String code);
}
