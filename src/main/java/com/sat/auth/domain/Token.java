package com.sat.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token_box",
        indexes = {
        @Index(columnList = "refreshToken", name = "refresh_idx")
})
@Entity
public class Token {
    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
    @Getter
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    public Token(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
