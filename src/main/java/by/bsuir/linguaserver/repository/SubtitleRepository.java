package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SubtitleRepository extends JpaRepository<Subtitle, UUID> {

    @Query("""
            select s from Subtitle s where
                s in (select vcl.subtitles from VideoContentLoc vcl where vcl.id = :videoContentLocId)
                and s.secondLanguage.id = :languageId
            """)
    Optional<Subtitle> findBy(@Param("videoContentLocId") UUID videoContentLocId,
                              @Param("languageId") Long languageId);
}
