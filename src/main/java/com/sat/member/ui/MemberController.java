package com.sat.member.ui;

import com.sat.member.application.MemberService;
import com.sat.member.application.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal String principal) {
        return ResponseEntity.ok(memberService.findBy(principal));
    }
}
