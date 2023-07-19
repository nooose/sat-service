package com.sat.utils;

import com.sat.auth.config.jwt.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/fake")
@RestController
public class FakeAuthenticationController {

    private final JwtProcessor jwtProcessor;

    public FakeAuthenticationController(JwtProcessor jwtProcessor) {
        this.jwtProcessor = jwtProcessor;
    }

    @GetMapping("/token")
    public ResponseEntity<Void> accessToken(@RequestParam String id) {
        String token = jwtProcessor.createToken(id).accessToken();
        return ResponseEntity.ok().header("X-Access-Token", token).build();
    }
}
