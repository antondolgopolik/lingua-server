package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.DuoWatchRequest;
import by.bsuir.linguaserver.entity.DuoWatchRequestStatus;
import by.bsuir.linguaserver.entity.DuoWatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DuoWatchResponseRepository extends JpaRepository<DuoWatchResponse, Long> {

    @Query("""
            SELECT dwr FROM DuoWatchResponse dwr
            WHERE
                dwr.responder.username = :username
                AND dwr.active
                AND (:status IS NULL OR dwr.duoWatchRequest.status = :status)
                AND LOWER(dwr.duoWatchRequest.videoContentLoc.videoContent.name) LIKE LOWER(:q)
            """)
    Page<DuoWatchResponse> findBy(@Param("username") String username,
                                  @Param("q") String q,
                                  @Param("status") DuoWatchRequestStatus status,
                                  Pageable pageable);
}
