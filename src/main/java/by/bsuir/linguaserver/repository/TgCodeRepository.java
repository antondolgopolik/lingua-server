package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.TgCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TgCodeRepository extends JpaRepository<TgCode, Long> {

    @Query("select tc from TgCode tc where tc.userId = :userId")
    Optional<TgCode> findOneByUserId(@Param("userId") Long userId);

    @Query("select tc from TgCode tc where tc.code = :code")
    Optional<TgCode> findOneByCode(@Param("code") String code);
}
