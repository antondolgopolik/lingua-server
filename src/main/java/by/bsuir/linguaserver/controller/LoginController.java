package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.LoginFormDto;
import by.bsuir.linguaserver.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginFormDto loginFormDto) {
        var token = new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
        var authenticate = authenticationManager.authenticate(token);
        return tokenService.generateToken(authenticate);
    }
}
