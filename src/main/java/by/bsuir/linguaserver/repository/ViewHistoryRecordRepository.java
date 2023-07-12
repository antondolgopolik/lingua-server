package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.entity.ViewHistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewHistoryRecordRepository extends JpaRepository<ViewHistoryRecord, Long> {

    List<ViewHistoryRecord> findAllByClientUsername(String username);
}
