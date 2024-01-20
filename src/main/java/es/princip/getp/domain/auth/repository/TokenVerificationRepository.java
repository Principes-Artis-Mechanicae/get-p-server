package es.princip.getp.domain.auth.repository;

import es.princip.getp.domain.auth.entity.TokenVerification;
import org.springframework.data.repository.CrudRepository;

public interface TokenVerificationRepository extends CrudRepository<TokenVerification, Long> {
    boolean existsByRefreshToken(String refreshToken);
}