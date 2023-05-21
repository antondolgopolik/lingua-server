package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    List<Dictionary> findAllByOwnerUsername(String username);
}
