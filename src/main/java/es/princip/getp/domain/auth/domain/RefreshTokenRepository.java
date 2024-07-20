package es.princip.getp.domain.auth.domain;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String refreshToken);
}