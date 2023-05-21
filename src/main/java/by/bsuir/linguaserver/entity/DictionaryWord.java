package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class DictionaryWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Dictionary dictionary;
    @NotBlank
    private String firstLanguageText;
    @NotBlank
    private String secondLanguageText;
    @NotBlank
    private String transcription;
    @NotNull
    private Integer repetitions;
    @NotNull
    private Integer correctRepetitions;
}
