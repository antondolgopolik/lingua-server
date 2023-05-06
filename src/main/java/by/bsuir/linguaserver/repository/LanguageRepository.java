package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
