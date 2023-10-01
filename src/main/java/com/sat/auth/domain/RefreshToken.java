package com.sat.auth.domain;

import com.sat.member.domain.MemberId;
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
@Getter
@Table(name = "token_box",
        indexes = {
        @Index(columnList = "refreshToken", name = "refresh_idx")
})
@Entity
public class RefreshToken {
    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private MemberId memberId;

    public RefreshToken(String refreshToken, MemberId memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }
}
