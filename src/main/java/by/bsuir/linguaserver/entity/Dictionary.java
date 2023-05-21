package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User owner;
    @NotBlank
    private String name;
    @ManyToOne
    private Language firstLanguage;
    @ManyToOne
    private Language secondLanguage;
    @OneToMany
    private Collection<DictionaryWord> dictionaryWords;
}
