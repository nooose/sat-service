package com.sat.auth.ui;

import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final AuthTokenRepository authTokenRepository;

    @GetMapping("/login-success")
    public String login() {
        return "index.html";
    }

    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<TokenPair> login(@RequestParam String code) {
        UUID id = UUID.fromString(code);
        TokenPair token = authTokenRepository.findById(id)
                .map(TokenPair::of)
                .orElseThrow();
        authTokenRepository.deleteById(id);
        return ResponseEntity.ok().body(token);
    }
}
