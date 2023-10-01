package com.sat.documentation;

import com.sat.auth.application.dto.TokenPair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends Documentation {

    @PostMapping("/token")
    public ResponseEntity<TokenPair> login(@RequestBody TokenRequest request) {
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzd...";
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzd...";
        return ResponseEntity.ok(new TokenPair(accessToken, refreshToken));
    }

    public record TokenRequest(
            String accessToken
    ) {

    }


    public record RefreshTokenRequest(
            String type,
            String refreshToken
    ) {

    }
}
