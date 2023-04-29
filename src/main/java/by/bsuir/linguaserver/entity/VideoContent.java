package by.bsuir.linguaserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class VideoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Positive
    private Integer duration;
    @NotNull
    @PositiveOrZero
    private Long views;
    @NotNull
    @PositiveOrZero
    private Long prevMonthViews;
}
