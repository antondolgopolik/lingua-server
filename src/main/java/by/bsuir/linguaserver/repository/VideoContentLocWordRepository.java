package by.bsuir.linguaserver.repository;

import by.bsuir.linguaserver.entity.VideoContentLoc;
import by.bsuir.linguaserver.entity.VideoContentLocWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface VideoContentLocWordRepository extends JpaRepository<VideoContentLocWord, Long> {

    ArrayList<VideoContentLocWord> findAllByVideoContentLoc(VideoContentLoc videoContentLoc);
}
