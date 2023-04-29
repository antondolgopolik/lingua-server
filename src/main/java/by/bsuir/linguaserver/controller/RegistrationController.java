package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.RegisterFormDto;
import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.facade.UserFacade;
import by.bsuir.linguaserver.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api")
@Slf4j
public class RegistrationController {

    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public RegistrationController(UserFacade userFacade,
                                  AuthenticationManager authenticationManager,
                                  TokenService tokenService) {
        this.userFacade = userFacade;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterFormDto registerFormDto) {
        Optional<User> user = userFacade.createUser(registerFormDto);
        String username = registerFormDto.getUsername();
        String password = registerFormDto.getPassword();
        if (user.isPresent()) {
            log.info("User with username {} was registered", username);
            var token = new UsernamePasswordAuthenticationToken(username, password);
            var authenticate = authenticationManager.authenticate(token);
            return ResponseEntity.ok(tokenService.generateToken(authenticate));
        } else {
            log.info("User with username {} wasn't registered", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
