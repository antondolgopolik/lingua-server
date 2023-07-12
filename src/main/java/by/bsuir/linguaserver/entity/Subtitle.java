package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Subtitle {
    @Id
    private UUID id;
    @ManyToOne
    private Language secondLanguage;
}
