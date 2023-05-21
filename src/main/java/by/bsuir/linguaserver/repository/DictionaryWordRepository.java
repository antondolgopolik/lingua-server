package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.DictionaryWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryWordRepository extends JpaRepository<DictionaryWord, Long> {

    List<DictionaryWord> findByDictionaryIdAndDictionaryOwnerUsername(Long dictionaryId, String username);
}
