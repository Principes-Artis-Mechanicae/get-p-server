package es.princip.getp.persistence.adapter.auth;

import es.princip.getp.domain.auth.RefreshToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String refreshToken);
}