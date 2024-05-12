package com.sat.user.ui.web

import com.sat.user.application.command.dto.LoginCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @PostMapping("/login")
    fun login(@RequestBody command: LoginCommand) {
        TODO("JWT 응답")
    }
}
