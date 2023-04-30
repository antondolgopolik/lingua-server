package by.bsuir.linguaserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public enum Role {
    ROLE_CLIENT, ROLE_CONTENT_MANAGER
}
