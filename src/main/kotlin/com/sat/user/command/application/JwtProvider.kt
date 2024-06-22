package com.sat.user.command.application

import com.sat.security.TokenConfigProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    private val config: TokenConfigProperties,
) {
    private val secretKey = Keys.hmacShaKeyFor(config.secretKey.toByteArray())

    fun createToken(principal: String, roles: List<String>, issueDate: Date): String {
        val claims = Jwts.claims().subject(principal).build()
        val expiration = Date(issueDate.time + config.expirationMills)

        return Jwts.builder()
            .claims(claims)
            .issuedAt(issueDate)
            .expiration(expiration)
            .claim("roles", roles)
            .signWith(secretKey)
            .compact()
    }

    fun principalBy(token: String): String {
        return validClaims(token).payload.subject
    }

    private fun validClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(secretKey)
            .decryptWith(secretKey)
            .build()
            .parseSignedClaims(token)
    }
}
