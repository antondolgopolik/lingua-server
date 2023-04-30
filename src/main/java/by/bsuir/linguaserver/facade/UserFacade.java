package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.dto.RegisterFormDto;
import by.bsuir.linguaserver.entity.Role;
import by.bsuir.linguaserver.entity.TgCode;
import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.service.TgCodeService;
import by.bsuir.linguaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class UserFacade {

    private final TgCodeService tgCodeService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserFacade(TgCodeService tgCodeService, UserService userService, PasswordEncoder passwordEncoder) {
        this.tgCodeService = tgCodeService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> createUser(RegisterFormDto registerForm) {
        Optional<TgCode> tgCode = tgCodeService.find(registerForm.getTgCode());
        if (tgCode.isPresent()) {
            User user = new User();
            user.setUsername(registerForm.getUsername());
            user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
            user.setTgId(tgCode.get().getUserId());
            user.setRoles(Collections.singleton(Role.ROLE_CLIENT));
            return Optional.of(userService.create(user));
        } else {
            return Optional.empty();
        }
    }
}
