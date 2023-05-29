package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class LoginResultDto {
    private String username;
    private List<Role> roles;
    private String token;
}
