package es.princip.getp.application.auth.service;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String refreshToken);
}