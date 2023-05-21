package by.bsuir.linguaserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class TgCode {
    @Id
    private Long chatId;
    @Column(unique = true)
    @NotBlank
    private String code;
}
