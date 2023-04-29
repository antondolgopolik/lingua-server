package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.VideoContent;
import jakarta.persistence.criteria.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideoContentRepository extends JpaRepository<VideoContent, UUID> {

}
