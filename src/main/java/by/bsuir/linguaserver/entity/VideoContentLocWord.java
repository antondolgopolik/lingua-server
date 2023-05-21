package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"video_content_loc_id", "word"})
})
@Data
public class VideoContentLocWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private VideoContentLoc videoContentLoc;
    @NotBlank
    private String word;
    @NotNull
    @Positive
    private Integer occurrence;
}
