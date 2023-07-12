package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface VideoContentRepository extends JpaRepository<VideoContent, UUID> {

    Page<VideoContent> findByNameContainingIgnoreCase(String name, Pageable pageable);

    ArrayList<VideoContent> findByNameContainingIgnoreCase(String name);
}
