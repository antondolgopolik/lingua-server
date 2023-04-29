package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private final TokenService tokenService;

    public AuthorizationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/auth")
    public void auth(@RequestParam String token, HttpServletResponse response) {
        if (tokenService.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
