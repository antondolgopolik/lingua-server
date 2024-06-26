package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.DuoWatchRequest;
import by.bsuir.linguaserver.entity.DuoWatchRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface DuoWatchRequestRepository extends JpaRepository<DuoWatchRequest, Long> {

    @Query("""
            SELECT dwr FROM DuoWatchRequest dwr
            WHERE
                dwr.status = 'OPEN'
                AND dwr.requester.username != :username
                AND LOWER(dwr.videoContentLoc.videoContent.name) LIKE LOWER(:q)
                AND (:videoContentLangId IS NULL OR dwr.videoContentLoc.language.id = :videoContentLangId)
                AND (:secondLangId IS NULL OR dwr.secondLanguage.id = :secondLangId) 
                AND :username NOT IN (SELECT dwre.responder.username FROM DuoWatchResponse dwre WHERE dwre.duoWatchRequest = dwr)
            """)
    ArrayList<DuoWatchRequest> findBy(@Param("username") String username,
                                      @Param("q") String q,
                                      @Param("videoContentLangId") Long videoContentLangId,
                                      @Param("secondLangId") Long secondLangId);

    @Query("""
            SELECT dwr FROM DuoWatchRequest dwr
            WHERE
                dwr.requester.username = :username
                AND (:status IS NULL OR dwr.status = :status)
                AND LOWER(dwr.videoContentLoc.videoContent.name) LIKE LOWER(:q)
            """)
    Page<DuoWatchRequest> findBy(@Param("username") String username,
                                 @Param("q") String q,
                                 @Param("status") DuoWatchRequestStatus status,
                                 Pageable pageable);
}
