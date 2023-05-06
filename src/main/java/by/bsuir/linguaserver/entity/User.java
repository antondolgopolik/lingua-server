package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Column(unique = true)
    @NotNull
    private Long tgId;
    @JoinTable
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    Collection<Role> roles;
}
