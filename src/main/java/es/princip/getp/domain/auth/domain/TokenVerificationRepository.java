package es.princip.getp.domain.auth.domain;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface TokenVerificationRepository extends KeyValueRepository<TokenVerification, Long> {
    boolean existsByRefreshToken(String refreshToken);
}