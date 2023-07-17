package com.sat.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Void> home() {
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@AuthenticationPrincipal String principal) {
        return ResponseEntity.ok().body("hello " + principal);
    }
}
