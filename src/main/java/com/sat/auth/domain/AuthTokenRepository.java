package com.sat.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
}
