package com.sat.auth.ui;

import com.sat.auth.application.LoginService;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.config.jwt.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/oauth2")
    public String login() {
        return "index.html";
    }

    @ResponseBody
    @PostMapping("/login/kakao")
    public ResponseEntity<TokenPair> login(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(loginService.login(tokenRequest));
    }
}
