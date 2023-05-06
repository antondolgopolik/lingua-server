package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.VideoContentLoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoContentLocRepository extends JpaRepository<VideoContentLoc, UUID> {
}
