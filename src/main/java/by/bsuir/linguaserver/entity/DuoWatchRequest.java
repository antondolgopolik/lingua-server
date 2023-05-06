package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
public class DuoWatchRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User requester;
    @ManyToOne
    private VideoContentLoc videoContentLoc;
    @ManyToOne
    private Language secondLanguage;
    @NotNull
    private LocalDate createdAt;
    @NotNull
    private LocalDate updatedAt;
    @Enumerated(EnumType.STRING)
    private DuoWatchRequestStatus status;
    @OneToMany
    private Collection<DuoWatchResponse> duoWatchResponses;
}
