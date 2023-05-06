package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
public class VideoContentLoc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private VideoContent videoContent;
    @ManyToOne
    private Language language;
    @OneToMany
    private Collection<Subtitle> subtitles;
}
