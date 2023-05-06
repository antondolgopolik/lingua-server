package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class DuoWatchResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private DuoWatchRequest duoWatchRequest;
    @ManyToOne
    private User responder;
    @NotNull
    private Boolean active;
}
