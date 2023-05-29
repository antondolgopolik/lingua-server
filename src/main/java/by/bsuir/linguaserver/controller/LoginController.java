package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.LoginFormDto;
import by.bsuir.linguaserver.dto.LoginResultDto;
import by.bsuir.linguaserver.entity.Role;
import by.bsuir.linguaserver.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public LoginResultDto login(@RequestBody LoginFormDto loginFormDto) {
        var token = new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(), loginFormDto.getPassword());
        var authentication = authenticationManager.authenticate(token);
        return buildLoginResult(authentication);
    }

    private LoginResultDto buildLoginResult(Authentication authentication) {
        LoginResultDto loginResultDto = new LoginResultDto();
        loginResultDto.setUsername(authentication.getName());
        loginResultDto.setRoles(authentication.getAuthorities().stream()
                .map((Function<GrantedAuthority, Role>) grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .toList());
        loginResultDto.setToken(tokenService.generateToken(authentication));
        log.info(loginResultDto.toString());
        return loginResultDto;
    }
}
